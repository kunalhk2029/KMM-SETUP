package com.saver.igv1.business.domain

import androidx.compose.runtime.MutableState
import com.saver.igv1.business.domain.models.player.PlayerMediaItemInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MediaPlayerProgressManager(
    private val videoPlayerManager: VideoPlayerManager
) {

    private val imageMediaPreviewTime = 5000L// 12 seconds
    private val imageMediaProgressUpdateInterval = 1L // 1 ms
    private val videoMediaProgressUpdateInterval = 1L // 1 ms
    private var activeMediaKey: Int? = null
    private var activeMediaItemPosition: HashMap<Int?, Int?>? = null
    private var mediaItems: HashMap<Int?, List<PlayerMediaItemInfo>?>? = null
    private var currentProgress = 0f
    private var progressUpdateStartCount = 0
    private var multiMediaPlayerEventListener: MultiMediaPlayerEventListener? = null
    private var onActiveMediaPlaybackCompleted: (() -> Unit)? = null
    var imageMediaProgressUpdateJob = Job()
    var videoMediaProgressUpdateJob = Job()

    fun updateImageMediaProgress(
        mediaItemProgressData: MutableState<Float>? = null,
        multiMediaPlayerEventListener: MultiMediaPlayerEventListener? = null,
        activeMediaKey: Int? = null,
        activeMediaItemPosition: HashMap<Int?, Int?>? = null,
        mediaItems: HashMap<Int?, List<PlayerMediaItemInfo>?>? = null,
        onActiveMediaPlaybackCompleted: () -> Unit
    ) {

        this.onActiveMediaPlaybackCompleted = onActiveMediaPlaybackCompleted
        this.multiMediaPlayerEventListener = multiMediaPlayerEventListener
        this.activeMediaKey = activeMediaKey
        this.activeMediaItemPosition = activeMediaItemPosition
        this.mediaItems = mediaItems

        if (imageMediaProgressUpdateJob.isActive) {
            imageMediaProgressUpdateJob.cancel()
        }
        imageMediaProgressUpdateJob = Job()

        CoroutineScope(Dispatchers.Default + imageMediaProgressUpdateJob).launch {
            ensureActive()
            val mediaDuration = imageMediaPreviewTime
            val progressUpdateInterval = imageMediaProgressUpdateInterval
            val progressUpdateCount = mediaDuration / progressUpdateInterval
            val progressUpdateStep = 1f / progressUpdateCount

            for (i in progressUpdateStartCount..progressUpdateCount) {
                progressUpdateStartCount++
                ensureActive()
                currentProgress = i * progressUpdateStep
                mediaItemProgressData?.value = currentProgress

                multiMediaPlayerEventListener?.onProgressUpdated(
                    mediaListPosition = this@MediaPlayerProgressManager.activeMediaKey!!,
                    mediaListItemPosition = this@MediaPlayerProgressManager.activeMediaItemPosition!![activeMediaKey]
                        ?: 0,
                    progress = currentProgress
                )

                if (currentProgress >= 1f && imageMediaProgressUpdateJob.isActive) {
                    imageMediaProgressUpdateJob.completeExceptionally(
                        Throwable(
                            IMAGE_PLAYBACK_COMPLETED
                        )
                    )
                }

                delay(progressUpdateInterval)
            }
        }

        imageMediaProgressUpdateJob.invokeOnCompletion {
            println("87868687 imageMediaProgressUpdateJob//// invokeOnCompletion ${it?.message}")
            if (it?.message == IMAGE_PLAYBACK_COMPLETED) {
                onActiveMediaPlaybackCompleted()
            }
        }
    }

    fun updateVideoMediaProgress(
        mediaItemProgressData: MutableState<Float>? = null,
        multiMediaPlayerEventListener: MultiMediaPlayerEventListener? = null,
        activeMediaKey: Int? = null,
        activeMediaItemPosition: HashMap<Int?, Int?>? = null,
        mediaItems: HashMap<Int?, List<PlayerMediaItemInfo>?>? = null,
    ) {
        this.multiMediaPlayerEventListener = multiMediaPlayerEventListener
        this.activeMediaKey = activeMediaKey
        this.activeMediaItemPosition = activeMediaItemPosition
        this.mediaItems = mediaItems

        if (videoMediaProgressUpdateJob.isActive) {
            videoMediaProgressUpdateJob.cancel()
        }
        videoMediaProgressUpdateJob = Job()

        CoroutineScope(Dispatchers.Default + videoMediaProgressUpdateJob).launch {
            ensureActive()
            while (videoMediaProgressUpdateJob.isActive) {
                ensureActive()

                withContext(Dispatchers.Main) {
                    currentProgress = if (videoPlayerManager.getDuration() > 0) {
                        ((videoPlayerManager.getCurrentPosition()
                            .toDouble() / videoPlayerManager.getDuration()) * 100).toFloat()
                    } else 0f
                }


                multiMediaPlayerEventListener?.onProgressUpdated(
                    mediaListPosition = this@MediaPlayerProgressManager.activeMediaKey!!,
                    mediaListItemPosition = this@MediaPlayerProgressManager.activeMediaItemPosition!![activeMediaKey]
                        ?: 0,
                    progress = currentProgress / 100f
                )

                mediaItemProgressData?.value = currentProgress / 100f

                if (currentProgress >= 100f) {
                    videoMediaProgressUpdateJob.complete()
                }

                delay(videoMediaProgressUpdateInterval)
            }
        }
    }

    fun resetProgressUpdateData() {
        videoPlayerManager.stopVideo()
        progressUpdateStartCount = 0
        currentProgress = 0f
        videoMediaProgressUpdateJob.cancel()
        imageMediaProgressUpdateJob.cancel()
        onActiveMediaPlaybackCompleted = null
    }

    fun handleMediaPause() {
        videoPlayerManager.pauseVideo()
        imageMediaProgressUpdateJob.cancel()
        videoMediaProgressUpdateJob.cancel()
    }

    fun destroyAll() {
        resetProgressUpdateData()
        videoPlayerManager.stopVideo()
        imageMediaProgressUpdateJob.cancel()
        videoMediaProgressUpdateJob.cancel()
        onActiveMediaPlaybackCompleted = null
    }

    fun handleMediaResume() {
        onActiveMediaPlaybackCompleted?.let {
            updateImageMediaProgress(
                multiMediaPlayerEventListener = multiMediaPlayerEventListener,
                activeMediaKey = activeMediaKey,
                activeMediaItemPosition = activeMediaItemPosition,
                mediaItems = mediaItems,
                onActiveMediaPlaybackCompleted = it
            )
        } ?: run {
            videoPlayerManager.resumeVideo()
        }
    }
}