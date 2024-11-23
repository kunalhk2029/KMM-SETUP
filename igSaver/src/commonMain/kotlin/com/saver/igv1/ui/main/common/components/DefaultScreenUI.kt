package com.saver.igv1.ui.main.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitHorizontalDragOrCancellation
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.saver.igv1.Colors
import com.saver.igv1.business.data.auth.InstagramAuthSessionManager
import com.saver.igv1.business.domain.MediaPlayerProgressManager
import com.saver.igv1.business.domain.ModalBottomSheetInfo
import com.saver.igv1.business.domain.ProgressBarState
import com.saver.igv1.business.domain.Screens
import com.saver.igv1.business.domain.SingleMediaPlayerManager
import com.saver.igv1.business.domain.UIComponent
import com.saver.igv1.getCurrentTimeInMillis
import com.saver.igv1.ui.main.common.bs.DownloadBs
import com.saver.igv1.ui.main.common.nav_drawer.NavDrawerBody
import com.saver.igv1.ui.main.common.nav_drawer.NavDrawerHeader
import com.saver.igv1.ui.main.common.nav_drawer.NavDrawerItem
import com.saver.igv1.ui.main.common.nav_drawer.drawerItemsList
import com.saver.igv1.ui.main.common.navbars.TopAppBar
import com.saver.igv1.ui.main.stories.preview.component.bs.SelectDownloadingOptionsBs
import com.saver.igv1.ui.main.stories.preview.component.bs.IgMentionedUserBs
import com.saver.igv1.ui.main.stories.preview.component.bs.SelectDownloadingLocationBs
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DefaultScreenUI(
    uiComponent: UIComponent? = UIComponent.None,
    progressBarState: ProgressBarState = ProgressBarState.Idle,
    isDrawerEnabled: Boolean = false,
    isTopBarVisible: Boolean = true,
    navController: NavController,
    modalBottomSheetInfo: ModalBottomSheetInfo? = null,
    sheetShape: RoundedCornerShape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp),
    onModalBottomSheetClosed: () -> Unit = {},
    mediaPlayerProgressManager: MediaPlayerProgressManager? = null,
    content: @Composable () -> Unit,
) {

    val instagramAuthSessionManager: InstagramAuthSessionManager = koinInject()
    val isLoggedIn = instagramAuthSessionManager.getSessionId() != null

    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
    )

    LaunchedEffect(modalBottomSheetState.isVisible) {
        if (!modalBottomSheetState.isVisible) {
            if (modalBottomSheetInfo != null) {
                mediaPlayerProgressManager?.handleMediaResume()
            }
            onModalBottomSheetClosed()
        }
    }

    LaunchedEffect(modalBottomSheetInfo) {
        if (modalBottomSheetInfo != null && modalBottomSheetInfo != ModalBottomSheetInfo.None) {
            modalBottomSheetState.show()
        } else {
            modalBottomSheetState.hide()
        }
    }

    ModalBottomSheetLayout(
        sheetBackgroundColor = Colors.grey,
        sheetState = modalBottomSheetState,
        sheetContent = {
            when (modalBottomSheetInfo) {

                is ModalBottomSheetInfo.IgMentionedUserSheet -> {
                    IgMentionedUserBs(
                        storyInteractionsMetaData = modalBottomSheetInfo.storyInteractionsMetaData,
                        modalBottomSheetState = modalBottomSheetState
                    )
                }

                ModalBottomSheetInfo.None -> {}
                is ModalBottomSheetInfo.StoryEmbeddedMediaSheet -> {}
                is ModalBottomSheetInfo.StoryEmbeddedHashtagSheet -> {}
                is ModalBottomSheetInfo.StoryEmbeddedLinkSheet -> {}
                is ModalBottomSheetInfo.StoryEmbeddedLocationSheet -> {}
                is ModalBottomSheetInfo.SelectDownloadOptionsBs -> {
                    SelectDownloadingOptionsBs(
                        modalBottomSheetInfo.downloadingOptions.collectAsState().value,
                        modalBottomSheetState,
                        modalBottomSheetInfo.onItemClicked
                    )
                }

                is ModalBottomSheetInfo.DownloadBs -> {
                    DownloadBs(
                        modalBottomSheetInfo, modalBottomSheetState
                    )
                }

                is ModalBottomSheetInfo.SelectDownloadLocationBs -> {
                    SelectDownloadingLocationBs(
                        modalBottomSheetState = modalBottomSheetState,
                        onDownloadingOptionSelected = modalBottomSheetInfo.onDownloadingOptionSelected
                    )
                }

                else -> {
                }
            }
        },
        scrimColor = Color.Transparent,
        sheetShape = sheetShape,
    ) {
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                if (isTopBarVisible)
                    TopAppBar(
                        "Insta Saver",
                        isHomeScreenVisible = true
                    ) {
                        coroutineScope.launch {
                            scaffoldState.drawerState.open()
                        }
                    }
            },
            drawerGesturesEnabled = isDrawerEnabled,
            drawerContent = {

                if (isDrawerEnabled) {

                    NavDrawerHeader()

                    NavDrawerBody(
                        items = drawerItemsList
                    ) {

                        when (it) {

                            NavDrawerItem.Stories -> {
                                if (isLoggedIn) {
                                    navController?.navigate(Screens.Stories)
                                } else {
                                    navController?.navigate(Screens.Security)
                                }
                            }

                            NavDrawerItem.AboutUs -> {
                                navController?.navigate(Screens.Security)
                            }

                            NavDrawerItem.DownloadedMedia -> {}
                            NavDrawerItem.HelpAndFeedback -> {}
                            NavDrawerItem.Highlights -> {}
                            NavDrawerItem.LinkDownload -> {}
                            NavDrawerItem.Posts -> {}
                            NavDrawerItem.PrivacyPolicy -> {}
                            NavDrawerItem.RateUs -> {}
                            NavDrawerItem.Reels -> {}
                            NavDrawerItem.Settings -> {}
                            NavDrawerItem.ShareApp -> {}
                            NavDrawerItem.TitleFavouritesUser -> {}
                            NavDrawerItem.ViewProfilePic -> {}
                        }
                    }
                }

            },
            drawerShape = RoundedCornerShape(topEnd = 4.dp, bottomEnd = 4.dp),
        ) {

            Box(
                modifier = Modifier.fillMaxSize()
                    .pointerInput(Unit) {

                        detectHorizontalDragGestures { change, dragAmount ->

                            if (dragAmount > 0) {
                                // Start detecting the drag from the left edge
                                if (change.position.x < 100) {  // Close to the left edge
                                    // Detect swipe movement towards the right
                                    if (dragAmount > 10f) { // If drag is greater than 10 pixels
//                                    onBackSwipeDetected()  // Trigger back swipe action
                                        println("87868687 onBackSwipeDetected: $it")
                                        navController.popBackStack()
                                    }
                                }
                            }

                        }
//                        detectHorizontalDragGestures { change, dragAmount ->
//                            println("87868687 dragAmount: $dragAmount")
//                        }


                    }
                    .background(MaterialTheme.colors.background),
                contentAlignment = Alignment.Center
            ) {

                content()

                if (uiComponent is UIComponent.DownloadingSingleMediaDialog) {
                    AlertDialog(
                        onDismissRequest = { },
                        title = {
                            Text(
                                "Downloading Story in ${uiComponent.downloadingLocationOption?.uiValue}"
                            )
                        },
                        text = {
                            "Downloading Story... ${uiComponent.progress}%"
                        },
                        confirmButton = {
                        },
                        dismissButton = {
                            Text("Pause")
                        })
                }

                if (progressBarState is ProgressBarState.Loading) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}


suspend fun PointerInputScope.detectSwipeSpeed(
    onSwipeSpeedDetected: (Float) -> Unit
) {
    awaitPointerEventScope {
        var startPosition: Offset? = null
        var startTime: Long? = null

        while (true) {
            // Capture the first touch down event
            val down =
                awaitPointerEvent(PointerEventPass.Initial).changes.firstOrNull { it.pressed }
            if (down != null) {
                // Track the position and time when the swipe starts
                startPosition = down.position
                startTime = getCurrentTimeInMillis()

                // Capture the movement of the swipe
                val drag = awaitHorizontalDragOrCancellation(down.id)

                if (drag != null) {
                    // Calculate the distance moved and the time taken
                    val endTime = getCurrentTimeInMillis()
                    val timeDiff = (endTime - startTime!!) / 1000f  // Time in seconds
                    val distance =
                        drag.positionChange().x  // Distance moved horizontally (in pixels)

                    // Calculate velocity (distance / time)
                    val velocity = distance / timeDiff

                    // Send the velocity (speed) to the callback
                    onSwipeSpeedDetected(velocity)
                }
            }
        }
    }
}


suspend fun PointerInputScope.detectSwipeBackGesture(
    onBackSwipeDetected: () -> Unit
) {
    awaitPointerEventScope {
        var startPosition: Offset? = null
        var startTime: Long? = null

        // Minimum swipe threshold (distance and time) to recognize back gesture
        val swipeThreshold = 100f  // Swipe distance threshold (in pixels)
        val minTimeForSwipe = 0.2f  // Time threshold in seconds for valid swipe (200ms)

        while (true) {
            val down =
                awaitPointerEvent(PointerEventPass.Initial).changes.firstOrNull { it.pressed }
            if (down != null) {
                // Capture the start position and time
                startPosition = down.position
                startTime = getCurrentTimeInMillis()

                // Track horizontal drag movement
                val drag = awaitHorizontalDragOrCancellation(down.id)

                if (drag != null) {
                    val currentPosition = drag.positionChange()  // Get the position change
                    val currentTime = getCurrentTimeInMillis()  // Get the current time

                    // Calculate the time difference in seconds
                    val timeDiff = (currentTime - startTime!!) / 1000f

                    // Check if swipe is left-to-right and duration is valid
                    if (currentPosition.x > 0 && timeDiff > minTimeForSwipe) {
                        if (currentPosition.x > swipeThreshold) {
                            // Trigger back swipe event
                            onBackSwipeDetected()
                        }
                    }
                }
            }
        }
    }
}