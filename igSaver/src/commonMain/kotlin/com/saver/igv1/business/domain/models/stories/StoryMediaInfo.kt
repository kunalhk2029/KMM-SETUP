package com.saver.igv1.business.domain.models.stories

import com.saver.igv1.business.data.network.storiesService.model.StoryImageCandidate
import com.saver.igv1.business.data.network.storiesService.model.StoryVideoVersions
import com.saver.igv1.business.domain.models.common.MediaDownloadOptions
import com.saver.igv1.business.domain.models.ig_user.InstagramUser
import kotlinx.coroutines.flow.MutableStateFlow


data class StoryMediaInfo(
    val id: String?,
    val isVideo: Boolean?,
    val imageMediaData: List<StoryImageCandidate>?,
    val videoMediaData: List<StoryVideoVersions>?,
    val videoDuration: Double?,
    val mediaTakenAtTimeStamp: Long?,
    val storyInteractionsMetaData: StoryInteractionsMetaData?,
    val isStoryMediaSeen: Boolean?,
    val user: InstagramUser?
) {
    fun formatSecondsToMMSS(durationInSeconds: Double): String {
        // Get the minutes and seconds
        val minutes = (durationInSeconds / 60).toInt()
        val seconds = (durationInSeconds % 60).toInt()

        // Format the string using string interpolation
        return "$minutes:${seconds.toString().padStart(2, '0')}"
    }
}


fun StoryMediaInfo.getStoryDownloadingOptions(): MutableStateFlow<List<MediaDownloadOptions>> {
    return MutableStateFlow(
        if (isVideo == true) {
            videoMediaData?.map { videoVersion ->
                MediaDownloadOptions(
                    url = videoVersion.url,
                    isVideo = true,
                    videoDuration = videoDuration,
                )
            } ?: listOf()
        } else {
            imageMediaData?.map {
                MediaDownloadOptions(
                    url = it.url,
                    isVideo = false,
                    height = it.height,
                    width = it.width
                )
            } ?: listOf()
        })
}


