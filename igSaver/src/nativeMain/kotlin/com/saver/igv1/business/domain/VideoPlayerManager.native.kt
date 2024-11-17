package com.saver.igv1.business.domain

import androidx.compose.runtime.remember
import kotlinx.cinterop.ExperimentalForeignApi
import platform.AVFoundation.AVPlayer
import platform.AVFoundation.AVPlayerItem
import platform.AVFoundation.AVPlayerLayer
import platform.AVFoundation.currentItem
import platform.AVFoundation.currentTime
import platform.AVFoundation.duration
import platform.AVFoundation.pause
import platform.AVFoundation.play
import platform.AVFoundation.replaceCurrentItemWithPlayerItem
import platform.AVKit.AVPlayerViewController
import platform.CoreMedia.CMTimeGetSeconds
import platform.Foundation.NSURL
import platform.UIKit.UIApplication
import kotlin.time.DurationUnit
import kotlin.time.toDuration

actual class VideoPlayerManager {


    var avPlayer: AVPlayer? = null

    var playbackLayer: AVPlayerLayer? = null
    val avPlayerViewController = AVPlayerViewController()

    init {
        avPlayerViewController.showsPlaybackControls = false
    }

    actual fun playVideo(
        url: String,
        releasedExoPlayerInfo: ReleasedExoPlayerInfo?,
        videoPlayerEventListener: VideoPlayerEventListener
    ): Any {
        val videoURL = NSURL(string = url)
        avPlayer = AVPlayer(videoURL)
        playbackLayer = AVPlayerLayer()
        playbackLayer?.player = avPlayer
        avPlayerViewController.player = avPlayer
        avPlayer?.play()
        return avPlayer!!
    }

    actual fun pauseVideo() {
        avPlayer?.pause()
    }

    actual fun resumeVideo() {
        avPlayer?.play()
    }

    actual fun stopVideo(): ReleasedExoPlayerInfo? {
        avPlayer?.pause()
        playbackLayer?.removeFromSuperlayer()
        playbackLayer = null
        avPlayer = null
        return null
    }


    @OptIn(ExperimentalForeignApi::class)
    actual fun getDuration(): Long {
        return avPlayer?.currentItem?.duration?.let {
            CMTimeGetSeconds(
                it
            ).toDuration(DurationUnit.MILLISECONDS).inWholeMilliseconds
        } ?: 0L
    }

    @OptIn(ExperimentalForeignApi::class)
    actual fun getCurrentPosition(): Long {
        val currentTime = avPlayer?.currentTime() // CMTime
        return currentTime?.let { CMTimeGetSeconds(it).toDuration(DurationUnit.MILLISECONDS).inWholeMilliseconds }
            ?: 0L
    }

}