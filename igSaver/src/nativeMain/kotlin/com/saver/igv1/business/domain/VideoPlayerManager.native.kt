package com.saver.igv1.business.domain

import kotlinx.cinterop.CValue
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
import com.saver.igv1.getCurrentTimeInMillis


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
    var currentTime = 0.0
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
//        isPlaying()
        startTimeObserver()
        avPlayer?.play()


        return avPlayer!!
    }


    @OptIn(ExperimentalForeignApi::class)
    private fun isPlaying() {
        CoroutineScope(Dispatchers.Default).launch {
            while (true) {
                if (
                    avPlayer?.timeControlStatus == AVPlayerTimeControlStatusPlaying
                ) {
                    val cmTime = CMTimeGetSeconds(avPlayer?.currentItem!!.duration)
                    duration =
                        if (cmTime.isNaN()) 0 else cmTime.toDuration(DurationUnit.MILLISECONDS).inWholeMilliseconds
                    println("56445 currentItem duration ${duration}")
                    videoPlayerEventListener.onVideoStarted()
                    break
                }
            }
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun startTimeObserver() {
        currentTime = 0.0
        var prev = getCurrentTimeInMillis()
        println("56445 prev  $prev")
        val observer = { time: CValue<CMTime> ->
            val curr = getCurrentTimeInMillis()
            val diff = curr - prev
            prev = curr
            println("56445 startTimeObserver diff $diff")
            val isBuffering = avPlayer?.currentItem?.isPlaybackLikelyToKeepUp() != true
            val isPlaying = avPlayer?.timeControlStatus == AVPlayerTimeControlStatusPlaying
            if (isPlaying && !isVideStartedSent) {
                isVideStartedSent = true
                videoPlayerEventListener.onVideoStarted()
            }
            if (isBuffering) {
                videoPlayerEventListener.onVideoBuffering()
            }
            if (isPlaying) {
                val rawTime = (CMTimeGetSeconds(time)) * 1000L
                println("56445  rawTime $rawTime")
//                val parsedTime = rawTime.toDuration(DurationUnit.MILLISECONDS).inWholeMilliseconds
                currentTime = rawTime
                println("56445 currentTime $currentTime")
//                currentTime = parsedTime
                if (avPlayer?.currentItem != null) {
                    avPlayer?.currentItem!!.duration
//                println("56445 currentItem duration ${avPlayer?.currentItem!!.duration}")
                    val cmTime = CMTimeGetSeconds(avPlayer?.currentItem!!.duration)
                    duration =
                        (if (cmTime.isNaN()) 0 else cmTime.toDuration(DurationUnit.MILLISECONDS).inWholeMilliseconds) * 1000L
                }
            }
        }
        val interval = CMTimeMakeWithSeconds(0.001, NSEC_PER_SEC.toInt())
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
    actual fun getCurrentPosition(): Double {
        return currentTime
    }

}