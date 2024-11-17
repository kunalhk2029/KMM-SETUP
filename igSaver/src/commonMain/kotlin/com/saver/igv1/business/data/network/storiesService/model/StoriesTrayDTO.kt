package com.saver.igv1.business.data.network.storiesService.model

import com.saver.igv1.business.domain.models.ig_user.InstagramUser
import com.saver.igv1.business.domain.models.stories.StoriesTrayInfo
import kotlinx.serialization.Serializable


@Serializable
data class StoriesTrayDTO(
    val tray: List<StoriesTrayItem>
)


@Serializable
data class StoriesTrayItem(
    val id: String,
    val latest_reel_media: Long,
    val seen: Long,
    val ranked_position: Int,
    val seen_ranked_position: Int,
    val media_count: Int,
    val muted: Boolean,
    val has_video: Boolean,
    val user: InstagramUser
) {
    fun mapToStoriesTrayInfo(): StoriesTrayInfo {
        return StoriesTrayInfo(
            id = id,
            rankedPosition = ranked_position,
            seenRankedPosition = seen_ranked_position,
            user = user,
            mediaCount = media_count,
            latestReelTime = latest_reel_media
        )
    }
}