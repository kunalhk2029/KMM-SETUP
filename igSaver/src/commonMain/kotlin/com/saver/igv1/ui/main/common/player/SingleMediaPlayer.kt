package com.saver.igv1.ui.main.common.player

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.awaitLongPressOrCancellation
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.changedToUp
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import com.saver.igv1.Colors
import com.saver.igv1.business.domain.ModalBottomSheetInfo
import com.saver.igv1.business.domain.SingleMediaPlayerManager
import com.saver.igv1.business.domain.StoryTouchManager
import com.saver.igv1.business.domain.models.player.PlayerMediaItemInfo
import com.saver.igv1.business.domain.models.stories.StoryInteractionsMetaData
import com.saver.igv1.ui.main.common.components.DefaultScreenUI
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SingleMediaPlayer(
    singleMediaPlayerManager: SingleMediaPlayerManager,
    list: List<PlayerMediaItemInfo>,
    startingIndex: Int,
    navController: NavController,
    modalBottomSheetInfo: ModalBottomSheetInfo?,
    onModalBottomSheetClosed: () -> Unit = {},
    onStoryInteractionsMetaDataTouched: (StoryInteractionsMetaData) -> Unit = {}
) {

    val coroutineScope = rememberCoroutineScope()

    val constraint = remember { mutableStateOf(Constraints()) }

    LaunchedEffect(list.size) {
        if (list.isNotEmpty()) {
            println("87868687 initMediaItems/// ")
            singleMediaPlayerManager.initMediaItems(list)
            singleMediaPlayerManager.startPlayingMedia(startingIndex)
        }
    }

    val activeMediaItem = singleMediaPlayerManager.activeMediaItem.collectAsState()

    DefaultScreenUI(
        isTopBarVisible = false,
        navController = navController,
        modalBottomSheetInfo = modalBottomSheetInfo,
        onModalBottomSheetClosed = onModalBottomSheetClosed
    ) {
        BoxWithConstraints(
            modifier = Modifier
        ) {

            constraint.value = constraints

            Column(
                modifier = Modifier.fillMaxSize()
                    .pointerInput(Unit) {

                        coroutineScope.launch {

                            awaitPointerEventScope {
                                while (true) {
                                    val down =
                                        awaitFirstDown() // Wait for the first pointer down event

                                    // Detect long press or interruption
                                    val longPress = awaitLongPressOrCancellation(down.id)

                                    if (longPress != null) {
                                        // Wait for to up event
                                        var isUpEvent = false
                                        while (!isUpEvent) {
                                            val event = awaitPointerEvent(PointerEventPass.Main)
                                            if (event.changes.all { it.changedToUp() }) {
                                                isUpEvent = true
                                                singleMediaPlayerManager.handleMediaResume()
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        detectTapGestures(onLongPress = {
                            singleMediaPlayerManager.handleMediaPause()
                            println("7677656 Long press detected!")
                        }, onTap = { offset ->

                            var isStoryInteractionMetaDataTouched = false
                            activeMediaItem.value?.let {
                                StoryTouchManager.getTouchedStoryInteractionsMetaData(
                                    offset = offset,
                                    constraint = constraints,
                                    storyTouchInteractionsMetaData =
                                    it.storyTouchInteractionsMetaData
                                )?.let {
                                    isStoryInteractionMetaDataTouched = true
                                    singleMediaPlayerManager.handleMediaPause()
                                    onStoryInteractionsMetaDataTouched.invoke(it)
                                }
                            }

                            if (!isStoryInteractionMetaDataTouched) {
                                singleMediaPlayerManager.handleMediaPause()
                                if (offset.x <= constraints.maxWidth / 2) {
                                    println("87868687 startPlayingMedia previous")
                                    singleMediaPlayerManager.startPlayingMedia(playPrev = true)

                                } else {
                                    println("87868687 startPlayingMedia next")
                                    singleMediaPlayerManager.startPlayingMedia(playNext = true)
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

                    Surface(
                        shape = RoundedCornerShape(8.dp), modifier = Modifier.height(4.dp)
                    ) {

                        Spacer(modifier = Modifier.width(2.dp))

                        LinearProgressIndicator(
                            progress = singleMediaPlayerManager.mediaItemProgressData.value,
                            color = Colors.blue,
                            modifier = Modifier.height(4.dp).fillMaxWidth()
                        )
                    }

                    Spacer(modifier = Modifier.width(2.dp))

                }

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {

                    AsyncImage(
                        model = activeMediaItem.value?.mediaUrl,
                        contentDescription = null,
                        onState = {

                            when (it) {

                                is AsyncImagePainter.State.Loading -> {

                                }

                                is AsyncImagePainter.State.Success -> {
                                    if (activeMediaItem.value?.isVideo == false) {
                                        println("87868687 startImageProgressUpdate")
                                        singleMediaPlayerManager.startImageProgressUpdate()
                                    }
                                }

                                is AsyncImagePainter.State.Error -> {

                                }

                                AsyncImagePainter.State.Empty -> {

                                }
                            }
                        },
                        modifier = Modifier.fillMaxSize()
                    )


                    if (activeMediaItem.value?.isVideo == true) {
                        PlayerView(
                            videoPlayerManager = singleMediaPlayerManager.getVideoPlayerManager(),
                            multipleMediaPlayerManager = null,
                            singleMediaPlayerManager = singleMediaPlayerManager,
                            modifier = Modifier.fillMaxSize(),
                            i = 0,
                            pagerState = null,
                            multiMediaPlayer = null,
                            singleMediaPlayer = singleMediaPlayerManager.player
                        )
                    }
                }
            }
        }
    }
}