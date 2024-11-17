package com.saver.igv1.business.data.db.stories.mappers

import com.saver.igv1.business.data.db.stories.entities.StoriesTrayInfoEntity
import com.saver.igv1.business.data.db.utils.EntityMapper
import com.saver.igv1.business.domain.models.stories.StoriesTrayInfo

class StoriesTrayEntityMapper() : EntityMapper<StoriesTrayInfo, StoriesTrayInfoEntity> {

    override fun mapToEntity(data: StoriesTrayInfo): StoriesTrayInfoEntity {
        return StoriesTrayInfoEntity(
            id = data.id ?: "",
            rankedPosition = data.rankedPosition,
            seenRankedPosition = data.seenRankedPosition,
            user = data.user,
            mediaCount = data.mediaCount,
            latestReelTime = data.latestReelTime,
            seenByUser = data.seenByUser
        )
    }

    override fun mapFromEntity(entity: StoriesTrayInfoEntity): StoriesTrayInfo {
        return StoriesTrayInfo(
            id = entity.id,
            rankedPosition = entity.rankedPosition,
            seenRankedPosition = entity.seenRankedPosition,
            user = entity.user,
            mediaCount = entity.mediaCount,
            latestReelTime = entity.latestReelTime
        )
    }
}