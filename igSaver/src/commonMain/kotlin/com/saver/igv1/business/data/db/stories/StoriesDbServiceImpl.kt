package com.saver.igv1.business.data.db.stories

import com.saver.igv1.business.data.db.stories.entities.StoryMediaInfoEntity
import com.saver.igv1.business.data.db.stories.mappers.StoriesMediaInfoEntityMapper
import com.saver.igv1.business.data.db.stories.mappers.StoriesTrayEntityMapper
import com.saver.igv1.business.domain.models.stories.StoriesTrayInfo
import com.saver.igv1.business.domain.models.stories.StoryMediaInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.withContext

class StoriesDbServiceImpl(
    private val storiesDao: StoriesDao,
    private val storiesTrayEntityMapper: StoriesTrayEntityMapper,
    private val storiesMediaInfoEntityMapper: StoriesMediaInfoEntityMapper
) : StoriesDbService {

    override suspend fun insertStoriesTrayInfo(storiesTrayInfo: List<StoriesTrayInfo>): LongArray {
        return withContext(Dispatchers.Default) {
            val storiesTrayInfoEntity =
                storiesTrayInfo.map { storiesTrayEntityMapper.mapToEntity(it) }
            storiesDao.insertStoriesTrayInfo(storiesTrayInfoEntity)
        }
    }

    override suspend fun deleteStoriesTrayInfo() {
        withContext(Dispatchers.Default) {
            storiesDao.deleteStoriesTrayInfoEntity()
        }
    }

    override suspend fun getStoriesTrayInfo(): List<StoriesTrayInfo> {
        return withContext(Dispatchers.Default) {
            storiesDao.getStoriesTrayInfo().map { storiesTrayEntityMapper.mapFromEntity(it) }
        }
    }

    override suspend fun insertStoryMediaItems(storyMediaInfo: List<StoryMediaInfo>): LongArray {
        return withContext(Dispatchers.Default) {
            storiesDao.insertStoryMediaItems(storyMediaInfo.map {
                storiesMediaInfoEntityMapper.mapToEntity(it)
            })
        }
    }

    override suspend fun deleteStoriesMediaInfoEntity() {
        withContext(Dispatchers.Default) {
            storiesDao.deleteStoriesMediaInfoEntity()
        }
    }

    override suspend fun getStoryMediaItem(
        userName: String,
        mediaTakenAtTimeStamp: Long
    ): StoryMediaInfo? {
        return withContext(Dispatchers.Default) {
            storiesDao.getStoryMediaItem(
                userName = userName,
                mediaTakenAtTimeStamp = mediaTakenAtTimeStamp
            )?.let { storiesMediaInfoEntityMapper.mapFromEntity(it) }
        }
    }

    override suspend fun getStoryMediaItems(): List<StoryMediaInfo> {
        return withContext(Dispatchers.Default) {
            storiesDao.getStoryMediaItems().map { storiesMediaInfoEntityMapper.mapFromEntity(it) }
        }
    }

    override suspend fun getStoryMediaItems(userName: String): List<StoryMediaInfo> {
        return withContext(Dispatchers.Default) {
            storiesDao.getStoryMediaItems(userName)
                .map { storiesMediaInfoEntityMapper.mapFromEntity(it) }
        }
    }

    override suspend fun getStoryMediaItemFlow(): Flow<List<StoryMediaInfo>> =
        channelFlow {
            storiesDao.getStoryMediaItemFlow().collect {
                send(it.map { storiesMediaInfoEntityMapper.mapFromEntity(it) })
            }
        }
}