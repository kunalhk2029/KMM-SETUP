package com.saver.igv1.ui.main.stories.preview.singletray

import com.saver.igv1.business.domain.DownloadingLocationOptions
import com.saver.igv1.business.domain.ModalBottomSheetInfo
import com.saver.igv1.business.domain.models.common.MediaDownloadOptions
import com.saver.igv1.business.domain.models.stories.StoryMediaInfo
import kotlinx.coroutines.flow.MutableStateFlow

sealed class SingleTrayPreviewEvents {

    data class InitStoryMediaData(
        val reelMediaList: MutableList<String>
    ) : SingleTrayPreviewEvents()

    data class UpdateActiveModalBottomSheetInfo(
        val modalBottomSheetInfo: ModalBottomSheetInfo?,
        val list: MutableStateFlow<List<MediaDownloadOptions>>? = null
    ) : SingleTrayPreviewEvents()

    data class StartDownloadingSingleStory(
        val downloadingLocationOptions: DownloadingLocationOptions,
        val mediaDownloadOptions: MediaDownloadOptions,
        val storyMediaInfo: StoryMediaInfo
    ) : SingleTrayPreviewEvents()
}


sealed class SingleTrayPreviewNavEvents {

    data object NavigateBack : SingleTrayPreviewNavEvents()

    data class NavigateToSingleMediaPlayer(
        val mediaPosition: Int,
        val userName: String
    ) : SingleTrayPreviewNavEvents()

}

