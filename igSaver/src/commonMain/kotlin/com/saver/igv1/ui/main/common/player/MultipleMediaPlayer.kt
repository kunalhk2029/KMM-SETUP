package com.saver.igv1.ui.main.common.player

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.changedToUp
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import com.saver.igv1.Colors
import com.saver.igv1.business.domain.MultipleMediaPlayerManager
import com.saver.igv1.business.domain.StoryTouchManager.getTouchedStoryInteractionsMetaData
import com.saver.igv1.business.domain.VideoPlayerManager
import com.saver.igv1.business.domain.models.player.PlayerMediaItemInfo
import com.saver.igv1.business.domain.models.stories.StoryInteractionsMetaData
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MultipleMediaPlayer(
    mediaItemList: List<PlayerMediaItemInfo>,
    videoPlayerManager: VideoPlayerManager,
    multipleMediaPlayerManager: MultipleMediaPlayerManager,
    i: Int,
    pagerState: PagerState,
    storyMediaItemsProgressData: Pair<Int?, Float>?,
    onStoryInteractionsMetaDataTouched: (StoryInteractionsMetaData) -> Unit = {}
) {


    val coroutineScope = rememberCoroutineScope()

    val isImageLoaded = remember { mutableStateOf(false) }

    val activeMediaItem = remember {
        mutableStateOf(
            mediaItemList[multipleMediaPlayerManager.getMediaPosition(i)]
        )
    }

    LaunchedEffect(Unit) {
        multipleMediaPlayerManager.activeMediaItemFlow.collect {
            if (it?.first == i)
                it.second?.let {
                    println("87868687 startPlayingMedia received activeMediaItem $it")
                    activeMediaItem.value = it
                }
        }
    }

    LaunchedEffect(pagerState.settledPage) {
        if (pagerState.settledPage == i) {
            multipleMediaPlayerManager.startPlayingMedia(i)
        }
    }

    LaunchedEffect(pagerState.settledPage, isImageLoaded.value) {
        if (pagerState.settledPage == i && isImageLoaded.value) {
            isImageLoaded.value = false
            println("87868687 startImageProgressUpdate")
            multipleMediaPlayerManager.startImageProgressUpdate()
        }
    }

    BoxWithConstraints() {

        val spacerWidth = 2.dp
        val availableWidth = maxWidth - (spacerWidth * (mediaItemList.size + 3))

        val widthOfEachPb =
            availableWidth / mediaItemList.size // Calculate width for each progress bar

        Column(
            modifier = Modifier.fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(onLongPress = {
                        multipleMediaPlayerManager.handleMediaPause()

                        coroutineScope.launch {
                            while (true) {
                                awaitPointerEventScope {
                                    val down =
                                        awaitFirstDown() // Wait for the first pointer down event
                                    println("Long press started at: ${down.position}")
                                    // Wait for long press or pointer up
                                    var isFingerUp = false
                                    while (!isFingerUp) {
                                        val event = awaitPointerEvent(PointerEventPass.Main)
                                        if (event.changes.all { it.changedToUp() }) {
                                            isFingerUp = true
                                            multipleMediaPlayerManager.handleMediaResume()
                                            println("Finger lifted after long press!")
                                        }
                                    }
                                }
                            }
                        }
                    }, onTap = { offset ->

                        var isStoryInteractionMetaDataTouched = false
                        activeMediaItem.value.let {
                            getTouchedStoryInteractionsMetaData(
                                offset = offset,
                                constraint = constraints,
                                storyTouchInteractionsMetaData =
                                it.storyTouchInteractionsMetaData
                            )?.let {
                                isStoryInteractionMetaDataTouched = true
                                multipleMediaPlayerManager.handleMediaPause()
                                onStoryInteractionsMetaDataTouched.invoke(it)
                            }
                        }

                        if (!isStoryInteractionMetaDataTouched) {
                            multipleMediaPlayerManager.handleMediaPause()
                            if (offset.x <= constraints.maxWidth / 2) {
                                println("87868687 startPlayingMedia previous")
                                multipleMediaPlayerManager.startPlayingMedia(playPrev = true)

                            } else {
                                println("87868687 startPlayingMedia next")
                                multipleMediaPlayerManager.startPlayingMedia(playNext = true)
                            }
                        }
                    })
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {

                mediaItemList.forEachIndexed { index, playerMediaItemInfo ->
                    Surface(
                        shape = RoundedCornerShape(8.dp), modifier = Modifier.height(4.dp)
                    ) {

                        if (index == 0) {
                            Spacer(modifier = Modifier.width(spacerWidth * 2))
                        }

                        LinearProgressIndicator(
                            progress =
                            if (index < (storyMediaItemsProgressData?.first ?: 0)) 1f
                            else if (index > (storyMediaItemsProgressData?.first ?: 0)) 0f
                            else storyMediaItemsProgressData?.second ?: 0f,
                            color = Colors.blue,
                            modifier = Modifier.height(4.dp).width(
                                widthOfEachPb
                            )
                        )
                    }

                    Spacer(
                        modifier = Modifier.width(
                            if (index == mediaItemList.size - 1) spacerWidth * 2
                            else spacerWidth
                        )
                    )
                }
            }

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {

                AsyncImage(
                    model = activeMediaItem.value.thumbNailUrl,
                    contentDescription = null,
                    onState = {

                        when (it) {

                            is AsyncImagePainter.State.Loading -> {

                            }

                            is AsyncImagePainter.State.Success -> {
                                if (activeMediaItem.value.isVideo == false) isImageLoaded.value =
                                    true
                            }

                            is AsyncImagePainter.State.Error -> {

                            }

                            AsyncImagePainter.State.Empty -> {

                            }
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )


                if (activeMediaItem.value.isVideo == true && !pagerState.isScrollInProgress) {
                    PlayerView(
                        videoPlayerManager = videoPlayerManager,
                        multipleMediaPlayerManager = multipleMediaPlayerManager,
                        singleMediaPlayerManager = null,
                        modifier = Modifier.fillMaxSize(),
                        i = i,
                        pagerState = pagerState,
                        multiMediaPlayer = multipleMediaPlayerManager.player,
                        singleMediaPlayer = null
                    )
                }
            }
        }
    }
}


