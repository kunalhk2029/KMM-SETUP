package com.saver.igv1.business.data.app_storage.external.stories

import com.saver.igv1.business.domain.DataState
import com.saver.igv1.business.domain.models.stories.StoryMediaInfo
import kotlinx.coroutines.flow.Flow

actual class StoriesExternalStorageServiceImpl : StoriesExternalStorageService{
    override suspend fun saveStory(
        storyMediaInfo: StoryMediaInfo,
        byteArray: ByteArray
    ): Flow<DataState<SavedMediaUri>> {
        TODO("Not yet implemented")
    }
}