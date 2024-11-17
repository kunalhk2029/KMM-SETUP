package com.saver.igv1.business.domain

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.saver.igv1.business.domain.models.player.PlayerMediaItemInfo
import io.ktor.utils.io.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

const val VIDEO_PAUSED = "VIDEO_PAUSED"
const val IMAGE_PLAYBACK_COMPLETED = "IMAGE_PLAYBACK_COMPLETED"

class MultipleMediaPlayerManager(
    private val mediaPlayerProgressManager: MediaPlayerProgressManager,
    private val videoPlayerManager: VideoPlayerManager,
) : VideoPlayerEventListener {

    private lateinit var multiMediaPlayerEventListener: MultiMediaPlayerEventListener
    private val videoItemResumeInfoData: HashMap<String?, ReleasedExoPlayerInfo?> = hashMapOf()
    private var mediaItems: HashMap<Int?, List<PlayerMediaItemInfo>?> = hashMapOf()
    private var activeMediaItemPosition: HashMap<Int?, Int?> = HashMap()
    private var activeMediaKey = 0
    var activeMediaItemFlow: MutableStateFlow<Pair<Int, PlayerMediaItemInfo?>?> =
        MutableStateFlow(null)
    val player: MutableState<Pair<Int, Any?>?> = mutableStateOf(null)
    val mediaListPositionUpdateFlow: MutableStateFlow<Int?> = MutableStateFlow(null)


    fun getMediaPosition(key: Int): Int {
        if ((activeMediaItemPosition[key]
                ?: 0) >= 0 && (activeMediaItemPosition[key]
                ?: 0) < (mediaItems[key]?.size
                ?: 0)
        ) {
            return activeMediaItemPosition[key] ?: 0
        }
        return 0
    }

    fun setMultiMediaPlayerEventListener(listener: MultiMediaPlayerEventListener) {
        if (!::multiMediaPlayerEventListener.isInitialized) {
            multiMediaPlayerEventListener = listener
        }
    }


    fun initMediaItems(
        items: HashMap<Int?, List<PlayerMediaItemInfo>?>
    ) {
        println("64564564 initMediaItems ${items.keys.size}")
        mediaItems = items
    }


    private fun updateActiveMediaItemIndex(position: Int) {
        activeMediaItemPosition[activeMediaKey] = position
    }

    fun getActiveMediaItem(): PlayerMediaItemInfo? {
        if ((activeMediaItemPosition[activeMediaKey]
                ?: 0) >= 0 && (activeMediaItemPosition[activeMediaKey]
                ?: 0) < (mediaItems[activeMediaKey]?.size
                ?: 0)
        ) {

            val activeMediaItem = mediaItems[activeMediaKey]?.get(
                activeMediaItemPosition[activeMediaKey] ?: 0
            )
            return activeMediaItem
        }
        return null
    }

    private fun isMediaItemVideo(): Boolean {
        return getActiveMediaItem()?.isVideo == true
    }

    fun startPlayingMedia(
        mediaListPosition: Int? = null,
        playNext: Boolean = false,
        playPrev: Boolean = false,
    ) {
        activeMediaKey = mediaListPosition ?: activeMediaKey

        if (playNext) {
            if ((activeMediaItemPosition[activeMediaKey]
                    ?: 0) < (mediaItems[activeMediaKey]?.size?.minus(1) ?: 0)
            ) {
                activeMediaItemPosition[activeMediaKey] =
                    (activeMediaItemPosition[activeMediaKey] ?: 0) + 1
            } else if (activeMediaKey < mediaItems.size - 1) {
                mediaListPositionUpdateFlow.value = activeMediaKey + 1
            }
        } else if (playPrev) {
            if ((activeMediaItemPosition[activeMediaKey] ?: 0) > 0) {
                activeMediaItemPosition[activeMediaKey] =
                    (activeMediaItemPosition[activeMediaKey] ?: 0) - 1
            } else if (activeMediaKey > 0) {
                mediaListPositionUpdateFlow.value = activeMediaKey - 1
            }
        }
        println("87868687 startPlayingMedia  activeMediaItemPosition ${activeMediaItemPosition[activeMediaKey]}")

        mediaPlayerProgressManager.resetProgressUpdateData()

        multiMediaPlayerEventListener.onProgressUpdated(
            mediaListPosition = activeMediaKey,
            mediaListItemPosition = activeMediaItemPosition[activeMediaKey]
                ?: 0,
            progress = 0f
        )

        val isVideo = isMediaItemVideo()
        if (isVideo) {
            player.value = null
            player.value = Pair(activeMediaKey, playVideo())
        } else {
            playImage()
        }
    }

    private fun onActiveMediaPlaybackCompleted() {
        CoroutineScope(Main).launch {
            if ((activeMediaItemPosition[activeMediaKey]
                    ?: 0) < (mediaItems[activeMediaKey]?.size?.minus(1) ?: 0)
            ) {
                updateActiveMediaItemIndex((activeMediaItemPosition[activeMediaKey] ?: 0) + 1)
                startPlayingMedia()
            } else if (activeMediaKey < mediaItems.size - 1) {
                mediaListPositionUpdateFlow.value = activeMediaKey + 1
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
            println("788797 playImage ${it}")
//            CoroutineScope(Main).launch {
            this@MultipleMediaPlayerManager.activeMediaItemFlow.value = Pair(activeMediaKey, it)
//            }
            multiMediaPlayerEventListener.onPlaybackStarted(
                activeMediaItemPosition[activeMediaKey] ?: 0, it
            )
        }
    }


    override fun onVideoStarted() {
        println("788797 onVideoStarted ")
        getActiveMediaItem()?.let {
            this@MultipleMediaPlayerManager.activeMediaItemFlow.value = Pair(activeMediaKey, it)
            multiMediaPlayerEventListener.onPlaybackStarted(
                activeMediaItemPosition[activeMediaKey] ?: 0, it
            )
        }
        mediaPlayerProgressManager.updateVideoMediaProgress(
            multiMediaPlayerEventListener = multiMediaPlayerEventListener,
            activeMediaKey = activeMediaKey,
            activeMediaItemPosition = activeMediaItemPosition,
            mediaItems = mediaItems
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
            activeMediaKey = activeMediaKey,
            activeMediaItemPosition = activeMediaItemPosition,
            mediaItems = mediaItems
        )
    }

    fun stopImageProgressUpdate() {
        println("788797 stopImageProgressUpdate ")
        mediaPlayerProgressManager.imageMediaProgressUpdateJob.cancel()
    }

    fun startImageProgressUpdate() {
        println("788797 startImageProgressUpdate ")
        mediaPlayerProgressManager.updateImageMediaProgress(
            multiMediaPlayerEventListener = multiMediaPlayerEventListener,
            activeMediaKey = activeMediaKey,
            activeMediaItemPosition = activeMediaItemPosition,
            mediaItems = mediaItems,
            onActiveMediaPlaybackCompleted = ::onActiveMediaPlaybackCompleted
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
                    multiMediaPlayerEventListener = multiMediaPlayerEventListener,
                    activeMediaKey = activeMediaKey,
                    activeMediaItemPosition = activeMediaItemPosition,
                    mediaItems = mediaItems,
                    onActiveMediaPlaybackCompleted = ::onActiveMediaPlaybackCompleted
                )
            }
        }
    }
}

interface MultiMediaPlayerEventListener {
    fun onProgressUpdated(
        mediaListPosition: Int, mediaListItemPosition: Int,
        progress: Float
    )

    fun onPlaybackStarted(mediaItemIndex: Int, mediaItemInfo: PlayerMediaItemInfo)
}
