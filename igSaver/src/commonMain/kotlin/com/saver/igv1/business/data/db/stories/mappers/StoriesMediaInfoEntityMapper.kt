package com.saver.igv1.business.data.db.stories.mappers

import com.saver.igv1.business.data.db.stories.entities.StoryMediaInfoEntity
import com.saver.igv1.business.data.db.utils.EntityMapper
import com.saver.igv1.business.domain.models.ig_user.InstagramUser
import com.saver.igv1.business.domain.models.stories.StoryMediaInfo

class StoriesMediaInfoEntityMapper() : EntityMapper<StoryMediaInfo, StoryMediaInfoEntity> {

    override fun mapToEntity(data: StoryMediaInfo): StoryMediaInfoEntity {
        return StoryMediaInfoEntity(
            id = data.id ?: "",
            isVideo = data.isVideo,
            mediaTakenAtTimeStamp = data.mediaTakenAtTimeStamp,
            imageMediaData = data.imageMediaData,
            videoMediaData = data.videoMediaData,
            isStoryMediaSeen = data.isStoryMediaSeen,
            storyTouchInteractionsMetaData = data.storyInteractionsMetaData,
            user = data.user,
            videoDuration = data.videoDuration
        )
    }

    override fun mapFromEntity(entity: StoryMediaInfoEntity): StoryMediaInfo {
        return StoryMediaInfo(
            id = entity.id,
            isVideo = entity.isVideo,
            mediaTakenAtTimeStamp = entity.mediaTakenAtTimeStamp,
            imageMediaData = entity.imageMediaData,
            videoMediaData = entity.videoMediaData,
            isStoryMediaSeen = entity.isStoryMediaSeen,
            storyInteractionsMetaData = entity.storyTouchInteractionsMetaData,
            user = entity.user,
            videoDuration = entity.videoDuration
        )
    }
}