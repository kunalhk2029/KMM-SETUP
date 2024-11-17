package com.saver.igv1.business.data.network.storiesService.model

import kotlinx.serialization.Serializable


//Link Model

@Serializable
data class StoryLinkStickerItem(
    val x: Double? = null,
    val y: Double? = null,
    val width: Double? = null,
    val height: Double? = null,
    val story_link: StoryLink? = null
)

@Serializable
data class StoryLink(
    val url: String? = null,
)

// IG Mention Model
@Serializable
data class StoryBloksStickerItem(
    val x: Double? = null,
    val y: Double? = null,
    val width: Double? = null,
    val height: Double? = null,
    val bloks_sticker: BloksSticker? = null
)

@Serializable
data class BloksSticker(
    val sticker_data: StickerData? = null,
)

@Serializable
data class StickerData(
    val ig_mention: IgMention? = null,
)


@Serializable
data class IgMention(
    val full_name: String? = null,
    val username: String? = null,
)


// Hashtag Model
@Serializable
data class StoryHashtags(
    val x: Double? = null,
    val y: Double? = null,
    val width: Double? = null,
    val height: Double? = null,
    val hashtag: Hashtag? = null
)

@Serializable
data class Hashtag(
    val name: String? = null,
    val id: String? = null,
)


// Music Model
@Serializable
data class StoryMusicItem(
    val id: String? = null,
    val music_asset_info: MusicAssetInfo? = null,
)

@Serializable
data class MusicAssetInfo(
    val title: String? = null,
    val display_artist: String? = null,
)


// Music Model
@Serializable
data class StoryLocationItem(
    val x: Double? = null,
    val y: Double? = null,
    val width: Double? = null,
    val height: Double? = null,
    val location: Location? = null,
)

@Serializable
data class Location(
    val pk: String? = null,
    val name: String? = null,
)

// Story Feed Model
@Serializable
data class StoryFeedMediaItem(
    val x: Double? = null,
    val y: Double? = null,
    val width: Double? = null,
    val height: Double? = null,
    val media_code: String? = null
)







