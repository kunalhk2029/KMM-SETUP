package com.saver.igv1.ui.main.common.player

import android.view.ViewGroup
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.saver.igv1.business.domain.MultipleMediaPlayerManager
import com.saver.igv1.business.domain.SingleMediaPlayerManager
import com.saver.igv1.business.domain.VideoPlayerManager

@Composable
actual fun PlayerView(
    videoPlayerManager: VideoPlayerManager,
    multipleMediaPlayerManager: MultipleMediaPlayerManager?,
    singleMediaPlayerManager: SingleMediaPlayerManager?,
    modifier: Modifier,
    i: Int,
    pagerState: PagerState?,
    multiMediaPlayer: MutableState<Pair<Int, Any?>?>?,
    singleMediaPlayer: MutableState<Any?>?,
) {

    val playerView: MutableState<PlayerView?> = remember {
        mutableStateOf(null)
    }

    LaunchedEffect(playerView.value, multiMediaPlayer?.value) {
        if (playerView.value == null || multiMediaPlayer?.value == null || multiMediaPlayer.value?.first != i) return@LaunchedEffect
        playerView.value?.player = multiMediaPlayer.value?.second as ExoPlayer
    }

    LaunchedEffect(playerView.value, singleMediaPlayer?.value) {
        if (playerView.value == null || singleMediaPlayer?.value == null) return@LaunchedEffect
        playerView.value?.player = singleMediaPlayer.value as ExoPlayer
    }

    AndroidView(
        factory = { context ->
            androidx.media3.ui.PlayerView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                useController = false
                playerView.value = this
            }

        },
        update = { playerview ->
            playerView.value = playerview

//            multipleMediaPlayerManager.startPlayingMedia()?.let {
//                playerView.player = it as ExoPlayer
//            }
        },
        modifier = modifier
    )


}