package com.saver.igv1.business.domain

import androidx.compose.runtime.Composable

expect class VideoPlayerManager {

    fun playVideo(
        url: String, releasedExoPlayerInfo: ReleasedExoPlayerInfo?,
        videoPlayerEventListener: VideoPlayerEventListener
    ): Any

    fun pauseVideo()
    fun resumeVideo()
    fun stopVideo(): ReleasedExoPlayerInfo?
    fun getDuration(): Long
    fun getCurrentPosition(): Double

}

data class ReleasedExoPlayerInfo(
    val playWhenReady: Boolean,
    val currentWindowIndex: Int,
    val playbackPosition: Long
)


interface VideoPlayerEventListener {
    fun onVideoStarted()
    fun onVideoEnded()
    fun onVideoPaused()
    fun onVideoResumed()
    fun onVideoBuffering()
    fun onPlayerIdle()
}