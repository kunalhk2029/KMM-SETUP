package com.saver.igv1.business.data.network.storiesService.model

import com.saver.igv1.business.domain.models.ig_user.InstagramUser
import kotlinx.serialization.Serializable


@Serializable
data class StoryMediaInfoDTO(
    val data: Data? = null
)

@Serializable
data class Data(
    val xdt_api__v1__feed__reels_media__connection: Xdt_api__v1__feed__reels_media__connection? = null
)

@Serializable
data class Xdt_api__v1__feed__reels_media__connection(
    val edges: List<StoryEdgeItem>? = null
)

@Serializable
data class StoryEdgeItem(
    val node: StoryNode? = null
)

@Serializable
data class StoryNode(
    val id: String? = null,
    val items: List<StoryMediaItem>? = null,
    val user: InstagramUser? = null,
    val seen: Long? = null,
    val latest_reel_media: Long? = null,
)


@Serializable
data class StoryMediaItem(
    val id: String? = null,
    val taken_at: Long? = null,
    val media_type: Int? = null,
    val image_versions2: StoryImageVersions? = null,
    val video_versions: List<StoryVideoVersions>? = null,
    val video_duration: Double? = null,
    val story_link_stickers: List<StoryLinkStickerItem>? = null,
    val story_bloks_stickers: List<StoryBloksStickerItem>? = null,
    val story_hashtags: List<StoryHashtags>? = null,
    val story_music_stickers: List<StoryMusicItem>? = null,
    val story_locations: List<StoryLocationItem>? = null,
    val story_feed_media: List<StoryFeedMediaItem>? = null,
    val expiring_at: Long? = null,
    val product_type: String? = null,
)

@Serializable
data class StoryImageVersions(
    val candidates: List<StoryImageCandidate>? = null
)

@Serializable
data class StoryImageCandidate(
    val height: Int? = null,
    val url: String? = null,
    val width: Int? = null
)

@Serializable
data class StoryVideoVersions(
    val type: Int? = null,
    val url: String? = null
)









