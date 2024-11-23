package com.saver.igv1.business.domain

import kotlinx.cinterop.CValue
import kotlinx.cinterop.ExperimentalForeignApi
import platform.AVFoundation.AVPlayer
import platform.AVFoundation.AVPlayerItemDidPlayToEndTimeNotification
import platform.AVFoundation.AVPlayerLayer
import platform.AVFoundation.AVPlayerTimeControlStatusPlaying
import platform.AVFoundation.addPeriodicTimeObserverForInterval
import platform.AVFoundation.currentItem
import platform.AVFoundation.duration
import platform.AVFoundation.isPlaybackLikelyToKeepUp
import platform.AVFoundation.pause
import platform.AVFoundation.play
import platform.AVFoundation.rate
import platform.AVFoundation.removeTimeObserver
import platform.AVFoundation.timeControlStatus
import platform.AVKit.AVPlayerViewController
import platform.CoreMedia.CMTime
import platform.CoreMedia.CMTimeGetSeconds
import platform.CoreMedia.CMTimeMakeWithSeconds
import platform.Foundation.NSNotificationCenter
import platform.Foundation.NSOperationQueue
import platform.Foundation.NSTimer
import platform.Foundation.NSURL
import platform.darwin.Float64
import platform.darwin.NSEC_PER_SEC
import platform.darwin.NSObject
import kotlin.time.DurationUnit
import kotlin.time.toDuration


actual class VideoPlayerManager {


    var avPlayer: AVPlayer? = null
    private lateinit var videoPlayerEventListener: VideoPlayerEventListener

    var playbackLayer: AVPlayerLayer? = null
    val avPlayerViewController = AVPlayerViewController()

    init {
        avPlayerViewController.showsPlaybackControls = false
    }

    var isVideStartedSent = false
    var isVideoEndSent = false
    var currentTime: Long = 0
    var duration: Long = 0
    private var timeObserver: Any? = null

    @OptIn(ExperimentalForeignApi::class)
    var observer: ((CValue<CMTime>) -> Unit)? = null


    @OptIn(ExperimentalForeignApi::class)
    actual fun playVideo(
        url: String,
        releasedExoPlayerInfo: ReleasedExoPlayerInfo?,
        videoPlayerEventListener: VideoPlayerEventListener
    ): Any {
        isVideStartedSent = false
        isVideoEndSent = false
        this.videoPlayerEventListener = videoPlayerEventListener
        val videoURL = NSURL(string = url)
        avPlayer = AVPlayer(videoURL)
        playbackLayer = AVPlayerLayer()
        playbackLayer?.player = avPlayer
        avPlayerViewController.player = avPlayer
        avPlayer?.play()

        startTimeObserver()
        return avPlayer!!
    }


    private fun isPlaying(): Boolean {
        return avPlayer?.rate?.toLong()?.toInt() != 0 && avPlayer?.error == null
    }


    @OptIn(ExperimentalForeignApi::class)
    private fun startTimeObserver() {
        val observer = { time: CValue<CMTime> ->
            val isBuffering = avPlayer?.currentItem?.isPlaybackLikelyToKeepUp() != true
            val isPlaying = avPlayer?.timeControlStatus == AVPlayerTimeControlStatusPlaying
            if (isPlaying && !isVideStartedSent) {
                isVideStartedSent = true
                videoPlayerEventListener.onVideoStarted()
            }
            if (isBuffering) {
                videoPlayerEventListener.onVideoBuffering()
            }
            val rawTime: Float64 = CMTimeGetSeconds(time)
            val parsedTime = rawTime.toDuration(DurationUnit.SECONDS).inWholeSeconds
            currentTime = parsedTime
            if (avPlayer?.currentItem != null) {
                val cmTime = CMTimeGetSeconds(avPlayer?.currentItem!!.duration)
                duration =
                    if (cmTime.isNaN()) 0 else cmTime.toDuration(DurationUnit.SECONDS).inWholeSeconds
            }
        }
        val interval = CMTimeMakeWithSeconds(1.0, NSEC_PER_SEC.toInt())
        timeObserver = avPlayer?.addPeriodicTimeObserverForInterval(interval, null, observer)
        NSNotificationCenter.defaultCenter.addObserverForName(
            name = AVPlayerItemDidPlayToEndTimeNotification,
            `object` = avPlayer?.currentItem,
            queue = NSOperationQueue.mainQueue,
            usingBlock = {
                videoPlayerEventListener.onVideoEnded()
            }
        )
    }


    actual fun pauseVideo() {
        avPlayer?.pause()
        if (::videoPlayerEventListener.isInitialized) {
            videoPlayerEventListener.onVideoPaused()
        }
    }

    actual fun resumeVideo() {
        avPlayer?.play()
        if (::videoPlayerEventListener.isInitialized) {
            videoPlayerEventListener.onVideoResumed()
        }
    }

    actual fun stopVideo(): ReleasedExoPlayerInfo? {
        avPlayer?.pause()
        timeObserver?.let { avPlayer?.removeTimeObserver(it) }
        timeObserver = null
        playbackLayer?.removeFromSuperlayer()
        playbackLayer = null
        avPlayer = null
        return null
    }


    @OptIn(ExperimentalForeignApi::class)
    actual fun getDuration(): Long {
        return duration
    }

    @OptIn(ExperimentalForeignApi::class)
    actual fun getCurrentPosition(): Long {
        return currentTime
    }

}