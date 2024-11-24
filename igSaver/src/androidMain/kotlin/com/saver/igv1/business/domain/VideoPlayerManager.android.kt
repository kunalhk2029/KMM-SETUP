package com.saver.igv1.business.domain

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.runBlocking


actual class VideoPlayerManager(
    private val context: Context
) {

    private var player: ExoPlayer? = null
    private lateinit var videoPlayerEventListener: VideoPlayerEventListener
    private var isExoPlayerAlreadyPlaying = false
    private val playerListener = object : Player.Listener {

        override fun onPlaybackStateChanged(state: Int) {
            super.onPlaybackStateChanged(state)

            when (state) {
                Player.STATE_READY -> {
                    videoPlayerEventListener.onVideoStarted()
                }

                Player.STATE_ENDED -> {
                    videoPlayerEventListener.onVideoEnded()
                }

                Player.STATE_BUFFERING -> {
                    videoPlayerEventListener.onVideoBuffering()
                }

                Player.STATE_IDLE -> {
                    videoPlayerEventListener.onPlayerIdle()

                }
            }
        }
    }

    actual fun playVideo(
        url: String,
        releasedExoPlayerInfo: ReleasedExoPlayerInfo?,
        videoPlayerEventListener: VideoPlayerEventListener,
        interval: Double?
    ): Any {
        this.videoPlayerEventListener = videoPlayerEventListener
        return prepareExoplayer(url, releasedExoPlayerInfo)
    }

    actual fun pauseVideo() {
        if (::videoPlayerEventListener.isInitialized) {
            videoPlayerEventListener.onVideoPaused()
        }
        player?.pause()
    }

    actual fun resumeVideo() {
        if (::videoPlayerEventListener.isInitialized) {
            videoPlayerEventListener.onVideoResumed()
        }
        player?.play()
    }


    actual fun getDuration(): Long {
        return player?.duration ?: 0L
    }

    actual fun getAndroidPlayerCurrentPlaybackPosition(): Long {
        return player?.currentPosition ?: 0L
    }

    actual fun getIosPlayerCurrentPlaybackPosition(): Double {
        return 0.0
    }

    actual fun stopVideo(): ReleasedExoPlayerInfo? {
        return releaseExoPlayer()
    }

    private fun prepareExoplayer(
        url: String,
        releasedExoPlayerInfo: ReleasedExoPlayerInfo?
    ): ExoPlayer {
        return getExoPlayer().also {
            val mediaItem = MediaItem.fromUri(url)
            it.playWhenReady =
                releasedExoPlayerInfo?.playWhenReady ?: true
            it.setMediaItem(mediaItem)
            releasedExoPlayerInfo?.let { releasedExoPlayerInfo ->
                it.seekToDefaultPosition(releasedExoPlayerInfo.currentWindowIndex)
                it.seekTo(releasedExoPlayerInfo.playbackPosition)
            }
            it.addListener(playerListener)
//            it.repeatMode = ExoPlayer.REPEAT_MODE_ALL
            it.prepare()
        }
    }


    private fun getExoPlayer(): ExoPlayer = runBlocking {
        if (isExoPlayerAlreadyPlaying) {
            releaseExoPlayer()
        }
        player = null
        isExoPlayerAlreadyPlaying = true
        player = ExoPlayer.Builder(context).build()
        player!!
    }


    private fun releaseExoPlayer(): ReleasedExoPlayerInfo {
        return player?.run {
            var playbackPosition = 0L
            var currentWindowIndex = 0
            if (currentPosition > 0) {
                playbackPosition = currentPosition
                currentWindowIndex = currentMediaItemIndex
            }
            val playwhenReady: Boolean = playWhenReady
            release()
            player = null
            isExoPlayerAlreadyPlaying = false
            return@run ReleasedExoPlayerInfo(
                playwhenReady,
                currentWindowIndex,
                playbackPosition
            )
        } ?: kotlin.run {
            isExoPlayerAlreadyPlaying = false
            return@run ReleasedExoPlayerInfo(true, 0, 0L)
        }
    }
}