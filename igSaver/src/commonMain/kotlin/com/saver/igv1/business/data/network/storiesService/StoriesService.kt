package com.saver.igv1.business.data.network.storiesService

import com.saver.igv1.business.domain.DataState
import com.saver.igv1.business.domain.models.stories.StoriesTrayInfo
import com.saver.igv1.business.domain.models.stories.StoryMediaInfo
import kotlinx.coroutines.flow.Flow

interface StoriesService {

    suspend fun getStoriesTrayInfo(): Flow<DataState<List<StoriesTrayInfo>?>>
    suspend fun getStoriesMediaInfo(
        variables: String,
        user: List<StoriesTrayInfo>
    ): Flow<DataState<List<StoryMediaInfo>?>>

    suspend fun getStoriesFileSize(
        url: String
    ): Double?
}