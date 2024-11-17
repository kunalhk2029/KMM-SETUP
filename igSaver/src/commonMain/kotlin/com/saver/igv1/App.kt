package com.saver.igv1

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Surface
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.compose.rememberNavController
import com.saver.igv1.ui.ig_auth.SecurityScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.saver.igv1.business.domain.MediaPlayerProgressManager
import com.saver.igv1.business.domain.Screens
import com.saver.igv1.business.domain.SingleMediaPlayerManager
import com.saver.igv1.ui.main.common.player.SingleMediaPlayer
import com.saver.igv1.ui.main.stories.preview.multipletray.MultipleTrayPreviewEvents
import com.saver.igv1.ui.main.stories.preview.multipletray.MultipleTrayPreviewScreen
import com.saver.igv1.ui.main.stories.preview.multipletray.MultipleTrayPreviewViewModel
import com.saver.igv1.ui.main.stories.preview.singletray.SingleTrayPreviewEvents
import com.saver.igv1.ui.main.stories.preview.singletray.SingleTrayPreviewNavEvents
import com.saver.igv1.ui.main.stories.preview.singletray.SingleTrayPreviewScreen
import com.saver.igv1.ui.main.stories.preview.singletray.SingleTrayPreviewViewModel
import com.saver.igv1.ui.main.stories.tray.StoriesTrayNavEvents
import com.saver.igv1.ui.main.stories.tray.StoriesTrayScreen
import com.saver.igv1.ui.main.stories.tray.StoriesTrayViewModel
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import ui.PasteAndDownloadLinkScreen


object Colors {

    val white = Color(0xFFffffff)
    val green = Color(0xFF32CD32)
    val black = Color(0xFF000000)
    val purple = Color(0xFF8134af)
    val blue = Color(0xFF02B1E6)
    val grey = Color(0xFF222222)

}

@Composable
@Preview
fun App(androidPlatformSpecificMethods: AndroidPlatformSpecificMethods? = null) {

    val lightColors = lightColors(
        primary = Color(0xFF4298f5),
        onPrimary = Color(0xFFffffff),
        background = Color(0xFFf0f2f2),
        onBackground = Color(0xFF000000),
        surface = Color(0xFFffffff),
    )

    val darkColors = darkColors(
        primary = Color(0xFF4298f5),
        onPrimary = Color(0xFFffffff),
        onSecondary = Color(0xFFffffff),
        background = Color(0xFF171717),
        onBackground = Color(0xFFffffff),
        surface = Color(0xFF202124),
        onSurface = Color(0xFFffffff),
    )

    val shapes = Shapes(
        small = RoundedCornerShape(4.dp),
        medium = RoundedCornerShape(10.dp),
        large = RoundedCornerShape(15.dp)
    )

    val isDarkTheme = isSystemInDarkTheme()

    MaterialTheme(
        shapes = shapes,
        colors = if (isDarkTheme) darkColors else lightColors
    ) {

        Surface(
            color = MaterialTheme.colors.background,
            modifier = Modifier.fillMaxSize()
        ) {
            val mediaPlayerProgressManager = koinInject<MediaPlayerProgressManager>()

            val navController = rememberNavController()

            val storyTrayViewModelOwner: StoryTrayViewModelOwner =
                remember { StoryTrayViewModelOwner() }

            LaunchedEffect(navController.currentBackStack) {
                navController.currentBackStack.collect {
                    val lastRoute = it.lastOrNull()?.destination?.route
                    if (
                        lastRoute?.contains("MultipleTrayPreview") == false &&
                        !lastRoute.contains("SingleMediaPlayer")
                    ) {
                        mediaPlayerProgressManager.destroyAll()
                    }
                }
            }

            NavHost(navController = navController, startDestination = Screens.PasteAndDownload) {

                composable<Screens.MultipleTrayPreview> {

                    val storiesTrayViewModel = koinViewModel<StoriesTrayViewModel>(
                        viewModelStoreOwner = storyTrayViewModelOwner
                    )

                    val multipleTrayPreviewViewModel = koinViewModel<MultipleTrayPreviewViewModel>()

                    LaunchedEffect(Unit) {
                        if (multipleTrayPreviewViewModel.state.value.trayInfo.isNullOrEmpty()) {
                            multipleTrayPreviewViewModel.onEvent(
                                MultipleTrayPreviewEvents.UpdateTrayInfoList(
                                    storiesTrayViewModel.state.value.trayInfo,
                                    storiesTrayViewModel.reelMediaList
                                )
                            )
                        }
                    }

                    MultipleTrayPreviewScreen(
                        multipleTrayPreviewViewModel.state.value,
                        multipleTrayPreviewViewModel::onEvent
                    ) {

                    }
                }

                composable<Screens.Stories>(
                ) {

                    val storiesTrayViewModel = koinViewModel<StoriesTrayViewModel>(
                        viewModelStoreOwner = storyTrayViewModelOwner
                    )

                    StoriesTrayScreen(
                        storiesTrayViewModel.state.value,
                        storiesTrayViewModel::onEvent
                    ) {
                        when (it) {
                            is StoriesTrayNavEvents.NavigateToMultipleTrayPreview -> {
                                it.storiesTrayItem.rankedPosition?.let { rankedPosition ->
                                    it.storiesTrayItem.user?.username?.let { username ->
                                        Screens.MultipleTrayPreview(
                                            rankedPosition
                                        )
                                    }?.let {
                                        navController.navigate(
                                            it
                                        )
                                    }
                                }
                            }

                            is StoriesTrayNavEvents.NavigateToSingleTrayPreview -> {
                                it.storiesTrayItem.rankedPosition?.let { rankedPosition ->
                                    it.storiesTrayItem.user?.username?.let { username ->
                                        Screens.SingleTrayPreview(
                                            rankedPosition,
                                            username
                                        )
                                    }?.let {
                                        navController.navigate(
                                            it
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                composable<Screens.SingleTrayPreview>(

                    ) {

                    val storiesTrayViewModel = koinViewModel<StoriesTrayViewModel>(
                        viewModelStoreOwner = storyTrayViewModelOwner
                    )

                    val singleTrayPreviewViewModel = koinViewModel<SingleTrayPreviewViewModel>()

                    LaunchedEffect(Unit) {
                        singleTrayPreviewViewModel.onEvent(
                            SingleTrayPreviewEvents.InitStoryMediaData(
                                storiesTrayViewModel.reelMediaList
                            )
                        )
                    }

                    SingleTrayPreviewScreen(
                        singleTrayPreviewViewModel.state.collectAsState().value,
                        singleTrayPreviewViewModel::onEvent
                    ) {
                        when (it) {

                            is SingleTrayPreviewNavEvents.NavigateToSingleMediaPlayer -> {
                                navController.navigate(
                                    Screens.SingleMediaPlayer(
                                        it.mediaPosition,
                                        it.userName
                                    )
                                )
                            }

                            SingleTrayPreviewNavEvents.NavigateBack -> {

                            }
                        }
                    }
                }

                composable<Screens.SingleMediaPlayer>(

                ) {

                    val singleMediaPlayerManager = koinInject<SingleMediaPlayerManager>()

                    val startingIndex = it.toRoute<Screens.SingleMediaPlayer>().mediaPosition
                    val userName = it.toRoute<Screens.SingleMediaPlayer>().userName

                    val singleTrayPreviewViewModel = koinViewModel<SingleTrayPreviewViewModel>()

                    LaunchedEffect(Unit) {
                        singleTrayPreviewViewModel.getPlayerMediaItemInfoList(userName)
                    }

                    SingleMediaPlayer(
                        singleMediaPlayerManager = singleMediaPlayerManager,
                        list = singleTrayPreviewViewModel.state.value.playerMediaItemsData ?: listOf(),
                        startingIndex = startingIndex,
                    )
                }

                composable<Screens.PasteAndDownload> {
                    PasteAndDownloadLinkScreen(navController)
                }

                composable<Screens.Security> {
                    SecurityScreen {
                        androidPlatformSpecificMethods?.StartLoginActivity()
                    }
                }
            }
        }

    }
}

class StoryTrayViewModelOwner : ViewModelStoreOwner {
    override val viewModelStore = ViewModelStore()
}

