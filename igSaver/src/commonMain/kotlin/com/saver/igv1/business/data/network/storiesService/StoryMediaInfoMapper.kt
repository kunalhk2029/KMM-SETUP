package com.saver.igv1.business.data.network.storiesService

import com.saver.igv1.business.data.network.storiesService.model.StoryMediaInfoDTO
import com.saver.igv1.business.data.network.utils.DTOMapper
import com.saver.igv1.business.domain.models.ig_user.InstagramUser
import com.saver.igv1.business.domain.models.stories.StoriesTrayInfo
import com.saver.igv1.business.domain.models.stories.StoryFeedMediaMetaData
import com.saver.igv1.business.domain.models.stories.StoryHashtagsMetaData
import com.saver.igv1.business.domain.models.stories.StoryIgMentionMetaData
import com.saver.igv1.business.domain.models.stories.StoryInteractionsMetaData
import com.saver.igv1.business.domain.models.stories.StoryLocationMetaData
import com.saver.igv1.business.domain.models.stories.StoryMediaInfo
import com.saver.igv1.business.domain.models.stories.StoryMusicMetaData
import com.saver.igv1.business.domain.models.stories.StoryStickerMetaData

class StoryMediaInfoMapper : DTOMapper<List<StoryMediaInfo>, StoryMediaInfoDTO> {

    var user: List<StoriesTrayInfo>? = null

    override fun mapToDomain(dto: StoryMediaInfoDTO?): List<StoryMediaInfo>? {
        return dto?.data?.xdt_api__v1__feed__reels_media__connection?.edges?.map { edge ->
            edge.node?.items?.map {
                StoryMediaInfo(
                    id = it.id,
                    isVideo = it.media_type == 2,
                    imageMediaData = it.image_versions2?.candidates,
                    videoMediaData = it.video_versions,
                    mediaTakenAtTimeStamp = it.taken_at,
                    storyInteractionsMetaData = StoryInteractionsMetaData(
                        storyFeedMediaMetaData = it.story_feed_media?.map {
                            StoryFeedMediaMetaData(
                                x = it.x,
                                y = it.x,
                                width = it.width,
                                height = it.height,
                                media_code = it.media_code
                            )
                        }, storyIgMentionMetaData = it.story_bloks_stickers?.map {
                            StoryIgMentionMetaData(
                                x = it.x,
                                y = it.y,
                                width = it.width,
                                height = it.height,
                                username = it.bloks_sticker?.sticker_data?.ig_mention?.username,
                                fullname = it.bloks_sticker?.sticker_data?.ig_mention?.full_name
                            )
                        },

                        storyStickerMetaData = it.story_link_stickers?.map {
                            StoryStickerMetaData(
                                x = it.x,
                                y = it.y,
                                width = it.width,
                                height = it.height,
                                url = it.story_link?.url,
                                linktitle = null
                            )
                        },
                        storyMusicMetaData = it.story_music_stickers?.map {
                            StoryMusicMetaData(
                                title = it.music_asset_info?.title,
                                display_artist = it.music_asset_info?.display_artist,
                            )
                        },
                        storyHashtagsMetaData = it.story_hashtags?.map {
                            StoryHashtagsMetaData(
                                x = it.x,
                                y = it.y,
                                width = it.width,
                                height = it.height,
                                name = it.hashtag?.name
                            )
                        },
                        storyLocationMetaData = it.story_locations?.map {
                            StoryLocationMetaData(
                                x = it.x,
                                y = it.y,
                                width = it.width,
                                height = it.height,
                                locationpk = it.location?.pk,
                                name = it.location?.name
                            )
                        }
                    ),
                    isStoryMediaSeen = false,
                    user =
                    user?.filter { trayInfo ->
                        trayInfo.user?.username == edge.node.user?.username
                    }?.firstOrNull()?.user,
                    videoDuration = it.video_duration
                )
            } ?: listOf()
        }?.flatten()
    }
}