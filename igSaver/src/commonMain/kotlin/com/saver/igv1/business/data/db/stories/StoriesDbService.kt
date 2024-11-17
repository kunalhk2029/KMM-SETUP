package com.saver.igv1.business.data.db.stories

import com.saver.igv1.business.data.db.stories.entities.StoryMediaInfoEntity
import com.saver.igv1.business.domain.models.stories.StoriesTrayInfo
import com.saver.igv1.business.domain.models.stories.StoryMediaInfo
import kotlinx.coroutines.flow.Flow

interface StoriesDbService {

    suspend fun insertStoriesTrayInfo(storiesTrayInfo: List<StoriesTrayInfo>): LongArray

    suspend fun deleteStoriesTrayInfo()

    suspend fun getStoriesTrayInfo(): List<StoriesTrayInfo>

    suspend fun insertStoryMediaItems(storyMediaInfo: List<StoryMediaInfo>): LongArray

    suspend fun deleteStoriesMediaInfoEntity()

    suspend fun getStoryMediaItems(): List<StoryMediaInfo>

    suspend fun getStoryMediaItems(userName: String): List<StoryMediaInfo>

    suspend fun getStoryMediaItemFlow(): Flow<List<StoryMediaInfo>>


    suspend fun getStoryMediaItem(
        userName: String,
        mediaTakenAtTimeStamp: Long
    ): StoryMediaInfo?
}