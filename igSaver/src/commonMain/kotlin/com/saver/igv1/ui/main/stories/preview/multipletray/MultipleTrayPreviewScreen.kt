package com.saver.igv1.ui.main.stories.preview.multipletray

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.saver.igv1.business.domain.MediaPlayerProgressManager
import com.saver.igv1.business.domain.MultiMediaPlayerEventListener
import com.saver.igv1.business.domain.MultipleMediaPlayerManager
import com.saver.igv1.business.domain.VideoPlayerManager
import com.saver.igv1.business.domain.getModalBottomSheetForTouchedStoryInteractions
import com.saver.igv1.business.domain.models.player.PlayerMediaItemInfo
import com.saver.igv1.ui.main.common.components.DefaultScreenUI
import com.saver.igv1.ui.main.common.player.MultipleMediaPlayer
import org.koin.compose.koinInject

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MultipleTrayPreviewScreen(
    multipleTrayPreviewState: MultipleTrayPreviewState,
    navController: NavController,
    onEvents: (MultipleTrayPreviewEvents) -> Unit,
    onNavEvents: (MultipleTrayPreviewNavEvents) -> Unit
) {

    val videoPlayerManager = koinInject<VideoPlayerManager>()
    val multipleMediaPlayerManager = koinInject<MultipleMediaPlayerManager>()
    val mediaPlayerProgressManager = koinInject<MediaPlayerProgressManager>()

    LaunchedEffect(Unit) {
        multipleMediaPlayerManager.setMultiMediaPlayerEventListener(listener = object :
            MultiMediaPlayerEventListener {

            override fun onProgressUpdated(
                mediaListPosition: Int,
                mediaListItemPosition: Int,
                progress: Float
            ) {
                onEvents(
                    MultipleTrayPreviewEvents.UpdateMediaItemProgressData(
                        mediaListPosition = mediaListPosition,
                        mediaListItemPosition = mediaListItemPosition,
                        progress = progress
                    )
                )
            }

            override fun onPlaybackStarted(
                mediaItemIndex: Int, mediaItemInfo: PlayerMediaItemInfo
            ) {

            }
        })
    }

    LaunchedEffect(
        multipleTrayPreviewState.storyMediaItemsData.value.size
    ) {
        val map = HashMap<Int?, List<PlayerMediaItemInfo>?>()
        multipleTrayPreviewState.storyMediaItemsData.value.keys.forEach {
            map[multipleTrayPreviewState.storyMediaItemsData.value[it]?.first] =
                multipleTrayPreviewState.storyMediaItemsData.value[it]?.second
        }
        multipleMediaPlayerManager.initMediaItems(
            map
        )
    }

    DefaultScreenUI(isTopBarVisible = false,
        progressBarState = multipleTrayPreviewState.progressBarState.collectAsState().value,
        modalBottomSheetInfo = multipleTrayPreviewState.activeModalBottomSheetInfo.collectAsState().value,
        mediaPlayerProgressManager = mediaPlayerProgressManager,
        navController = navController,
        onModalBottomSheetClosed = {
            onEvents(
                MultipleTrayPreviewEvents.UpdateActiveModalBottomSheetInfo(
                    null
                )
            )
        }) {

        Column(modifier = Modifier.fillMaxSize().padding(vertical = 20.dp)) {

            if (multipleTrayPreviewState.trayInfo != null && multipleTrayPreviewState.currentTrayItemPos != null) {

                val pagerState = multipleTrayPreviewState.currentTrayItemPos?.let {
                    multipleTrayPreviewState.trayInfo?.let { trayList ->
                        rememberPagerState(initialPage = it - 1, pageCount = { trayList.size })
                    }
                }

                LaunchedEffect(pagerState?.isScrollInProgress) {
                    if (pagerState?.isScrollInProgress == true) {
                        multipleMediaPlayerManager.handleMediaPause()
                    } else {
                        multipleMediaPlayerManager.handleMediaResume()
                    }
                }

                LaunchedEffect(pagerState?.settledPage) {
                    onEvents(MultipleTrayPreviewEvents.UpdateCurrentTrayItemPosition(pagerState?.currentPage))
                    pagerState?.settledPage?.let {
                        onEvents(
                            MultipleTrayPreviewEvents.InitMediaPlayerItemsData(
                                position = it
                            )
                        )
                    }
                }

                LaunchedEffect(multipleMediaPlayerManager.mediaListPositionUpdateFlow.value) {
                    multipleMediaPlayerManager.mediaListPositionUpdateFlow.collect {
                        it?.let { it1 -> pagerState?.scrollToPage(it1) }
                    }
                }

                if (pagerState != null) {

                    HorizontalPager(
                        state = pagerState,
                    ) {

                        val currentTrayItem = multipleTrayPreviewState.trayInfo?.get(it)

                        multipleTrayPreviewState.storyMediaItemsData.value[currentTrayItem?.user?.username]?.second?.let { it1 ->
                            MultipleMediaPlayer(
                                mediaItemList = it1,
                                videoPlayerManager = videoPlayerManager,
                                multipleMediaPlayerManager = multipleMediaPlayerManager,
                                it,
                                pagerState,
                                multipleTrayPreviewState.storyMediaItemsProgressData.collectAsState().value[it],
                            ) {
                                onEvents(
                                    MultipleTrayPreviewEvents.UpdateActiveModalBottomSheetInfo(
                                        getModalBottomSheetForTouchedStoryInteractions(it)
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}