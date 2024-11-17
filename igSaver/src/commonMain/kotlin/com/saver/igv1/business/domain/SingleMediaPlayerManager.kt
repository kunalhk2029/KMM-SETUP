package com.saver.igv1.business.domain

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.saver.igv1.business.domain.models.player.PlayerMediaItemInfo
import io.ktor.utils.io.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


class SingleMediaPlayerManager(
    private val videoPlayerManager: VideoPlayerManager,
    private val mediaPlayerProgressManager: MediaPlayerProgressManager
) : VideoPlayerEventListener {


    private val videoItemResumeInfoData: HashMap<String?, ReleasedExoPlayerInfo?> = hashMapOf()
    private var mediaItems: List<PlayerMediaItemInfo>? = listOf()

    var activeMediaItem: MutableStateFlow<PlayerMediaItemInfo?> =
        MutableStateFlow(null)
    var activeMediaItemPosition: Int = 0
    val mediaItemProgressData: MutableState<Float> = mutableStateOf(0f)
    val player: MutableState<Any?> = mutableStateOf(null)

    fun initMediaItems(
        items: List<PlayerMediaItemInfo>
    ) {
        println("87868687 initMediaItems size ${mediaItems?.size} ")
        mediaItems = items
    }

    fun getVideoPlayerManager(): VideoPlayerManager {
        return videoPlayerManager
    }


    private fun updateActiveMediaItemIndex(position: Int) {
        activeMediaItemPosition = position
    }

    fun getActiveMediaItem(): PlayerMediaItemInfo? {
        if (activeMediaItemPosition >= 0 && activeMediaItemPosition < (mediaItems?.size ?: 0)
        ) {

            val activeMediaItem = mediaItems?.get(activeMediaItemPosition)
            return activeMediaItem
        }
        return null
    }

    private fun isMediaItemVideo(): Boolean {
        return getActiveMediaItem()?.isVideo == true
    }

    fun startPlayingMedia(
        position: Int? = null,
        playNext: Boolean = false,
        playPrev: Boolean = false,
    ) {
        activeMediaItemPosition = position ?: activeMediaItemPosition
        if (playNext) {
            if (activeMediaItemPosition < (mediaItems?.size?.minus(1) ?: 0)
            ) {
                activeMediaItemPosition += 1
            }
        } else if (playPrev) {
            if (activeMediaItemPosition > 0) {
                activeMediaItemPosition -= 1
            }
        }
        println("87868687 startPlayingMedia  activeMediaItemPosition ${activeMediaItemPosition}")

        mediaPlayerProgressManager.resetProgressUpdateData()

        val isVideo = isMediaItemVideo()
        if (isVideo) {
            player.value = null
            player.value = playVideo()
        } else {
            playImage()
        }
    }


    private fun onActiveMediaPlaybackCompleted() {
        CoroutineScope(Main).launch {
            if (activeMediaItemPosition < (mediaItems?.size?.minus(1) ?: 0)
            ) {
                updateActiveMediaItemIndex(activeMediaItemPosition + 1)
                startPlayingMedia()
            }
        }
    }

    private fun playVideo(): Any? {
        return getActiveMediaItem()?.let { mediaItem ->
            mediaItem.mediaUrl?.let {
                videoPlayerManager.playVideo(it, videoItemResumeInfoData[mediaItem.id], this)
            }
        }
    }

    private fun playImage() {
        getActiveMediaItem()?.let {
            activeMediaItem.value = it
            println("788797 playImage ${it}")
        }
    }


    override fun onVideoStarted() {
        getActiveMediaItem()?.let {
            println("788797 onVideoStarted ${it.isVideo}")
            activeMediaItem.value = it
        }
        mediaPlayerProgressManager.updateVideoMediaProgress(
            mediaItemProgressData
        )
    }

    override fun onVideoEnded() {
        println("788797 onVideoEnded ")
        onActiveMediaPlaybackCompleted()
    }

    override fun onVideoBuffering() {
        println("788797 onVideoBuffering ")
    }

    override fun onPlayerIdle() {
        println("788797 onPlayerIdle ")

    }

    override fun onVideoPaused() {
        println("87868687 onVideoPaused ")
//        videoItemResumeInfoData[getActiveMediaItem()?.id] = videoPlayerManager.stopVideo()
        mediaPlayerProgressManager.videoMediaProgressUpdateJob.cancel(
            CancellationException(
                VIDEO_PAUSED
            )
        )
    }

    override fun onVideoResumed() {
        println("788797 onVideoResumed ")
        mediaPlayerProgressManager.updateVideoMediaProgress(
            mediaItemProgressData
        )
    }

    fun stopImageProgressUpdate() {
        println("788797 stopImageProgressUpdate ")
        mediaPlayerProgressManager.imageMediaProgressUpdateJob.cancel()
    }

    fun startImageProgressUpdate() {
        println("788797 startImageProgressUpdate ")
        mediaPlayerProgressManager.updateImageMediaProgress(
            mediaItemProgressData = mediaItemProgressData,
            onActiveMediaPlaybackCompleted = this::onActiveMediaPlaybackCompleted
        )
    }

    fun handleMediaPause() {
        videoPlayerManager.pauseVideo()
        stopImageProgressUpdate()
    }

    fun handleMediaResume() {
        if (getActiveMediaItem() != null) {
            if (isMediaItemVideo()) {
                videoPlayerManager.resumeVideo()
            } else {
                mediaPlayerProgressManager.updateImageMediaProgress(
                    mediaItemProgressData,
                    onActiveMediaPlaybackCompleted = this::onActiveMediaPlaybackCompleted
                )
            }
        }
    }
}