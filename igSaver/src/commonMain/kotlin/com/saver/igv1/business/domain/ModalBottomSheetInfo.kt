package com.saver.igv1.business.domain

import com.saver.igv1.business.domain.models.common.MediaDownloadOptions
import com.saver.igv1.business.domain.models.stories.StoryInteractionsMetaData
import com.saver.igv1.business.domain.models.stories.StoryMediaInfo
import kotlinx.coroutines.flow.MutableStateFlow

sealed class ModalBottomSheetInfo {

    data object None : ModalBottomSheetInfo()

    data class IgMentionedUserSheet(val storyInteractionsMetaData: StoryInteractionsMetaData) :
        ModalBottomSheetInfo()

    data class StoryEmbeddedLinkSheet(val storyInteractionsMetaData: StoryInteractionsMetaData) :
        ModalBottomSheetInfo()

    data class StoryEmbeddedMediaSheet(val storyInteractionsMetaData: StoryInteractionsMetaData) :
        ModalBottomSheetInfo()

    data class StoryEmbeddedHashtagSheet(val storyInteractionsMetaData: StoryInteractionsMetaData) :
        ModalBottomSheetInfo()

    data class StoryEmbeddedLocationSheet(val storyInteractionsMetaData: StoryInteractionsMetaData) :
        ModalBottomSheetInfo()

    data class DownloadBs(
        val onDownloadClicked: () -> Unit,
        val onPlayClicked: () -> Unit,
        val onShareClicked: () -> Unit
    ) : ModalBottomSheetInfo()


    data class SelectDownloadOptionsBs(
        val downloadingOptions: MutableStateFlow<List<MediaDownloadOptions>>,
        val onItemClicked: (MediaDownloadOptions) -> Unit
    ) : ModalBottomSheetInfo()


    data class SelectDownloadLocationBs(
        val mediaDownloadOptions: MediaDownloadOptions,
        val storyMediaInfo: StoryMediaInfo,
        val onDownloadingOptionSelected: (SelectedDownloadingLocationOptionData) -> Unit = {},
    ) : ModalBottomSheetInfo()

}


fun getModalBottomSheetForTouchedStoryInteractions(storyInteractionsMetaData: StoryInteractionsMetaData): ModalBottomSheetInfo? {

    return when {
        storyInteractionsMetaData.storyIgMentionMetaData?.isNotEmpty() == true -> {
            ModalBottomSheetInfo.IgMentionedUserSheet(storyInteractionsMetaData)
        }

        storyInteractionsMetaData.storyStickerMetaData?.isNotEmpty() == true -> {
            ModalBottomSheetInfo.StoryEmbeddedLinkSheet(storyInteractionsMetaData)
        }

        storyInteractionsMetaData.storyFeedMediaMetaData?.isNotEmpty() == true -> {
            ModalBottomSheetInfo.StoryEmbeddedMediaSheet(storyInteractionsMetaData)
        }

        storyInteractionsMetaData.storyHashtagsMetaData?.isNotEmpty() == true -> {
            ModalBottomSheetInfo.StoryEmbeddedHashtagSheet(storyInteractionsMetaData)
        }

        storyInteractionsMetaData.storyLocationMetaData?.isNotEmpty() == true -> {
            ModalBottomSheetInfo.StoryEmbeddedLocationSheet(storyInteractionsMetaData)
        }

        else -> {
            null
        }
    }
}