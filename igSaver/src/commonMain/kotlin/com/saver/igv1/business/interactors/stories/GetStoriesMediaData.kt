package com.saver.igv1.business.interactors.stories

import androidx.compose.runtime.rememberCoroutineScope
import com.saver.igv1.business.data.db.stories.StoriesDbService
import com.saver.igv1.business.domain.models.ig_user.InstagramUser
import com.saver.igv1.business.domain.models.stories.StoryMediaInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

class GetStoriesMediaData(
    private val storiesDbService: StoriesDbService
) {

    suspend operator fun invoke(): Flow<List<StoryMediaInfo>> {

        return storiesDbService.getStoryMediaItemFlow()

    }
}