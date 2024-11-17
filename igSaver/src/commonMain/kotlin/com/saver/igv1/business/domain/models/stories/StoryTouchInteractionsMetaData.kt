package com.saver.igv1.business.domain.models.stories

import kotlinx.serialization.Serializable


@Serializable
data class StoryFeedMediaMetaData(
    val x: Double?,
    val y: Double?,
    val width: Double?,
    val height: Double?,
    val media_code: String?
)

@Serializable
data class StoryIgMentionMetaData(
    val x: Double?,
    val y: Double?,
    val width: Double?,
    val height: Double?,
    val username: String?,
    val fullname: String?,
    val profile_url: String? = null
)

@Serializable
data class StoryStickerMetaData(
    val x: Double?,
    val y: Double?,
    val width: Double?,
    val height: Double?,
    val url: String?,
    val linktitle: String?
)

@Serializable
data class StoryMusicMetaData(
    val title: String?,
    val display_artist: String?,
    val cover_artwork_thumbnail_uri: String? = null,
    val progressive_download_url: String? = null,
    val artistiglink: String? = null,
    val placeholder_profile_pic_url: String? = null,
    val musicidforofflineprofile: String? = null,
    val artistigisverified: Boolean? = null,
    val igartistprofileurl: String? = null
)

@Serializable
data class StoryHashtagsMetaData(
    val x: Double?,
    val y: Double?,
    val width: Double?,
    val height: Double?,
    val name: String?
)

@Serializable
data class StoryLocationMetaData(
    val x: Double?,
    val y: Double?,
    val width: Double?,
    val height: Double?,
    val name: String?,
    val locationpk: String?
)


@Serializable
data class StoryInteractionsMetaData(
    val storyFeedMediaMetaData: List<StoryFeedMediaMetaData>? = null,
    val storyIgMentionMetaData: List<StoryIgMentionMetaData>? = null,
    val storyStickerMetaData: List<StoryStickerMetaData>? = null,
    val storyMusicMetaData: List<StoryMusicMetaData>? = null,
    val storyHashtagsMetaData: List<StoryHashtagsMetaData>? = null,
    val storyLocationMetaData: List<StoryLocationMetaData>? = null
)