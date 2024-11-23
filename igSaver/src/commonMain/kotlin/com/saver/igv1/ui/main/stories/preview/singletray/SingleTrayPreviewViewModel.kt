package com.saver.igv1.ui.main.stories.preview.singletray

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saver.igv1.business.data.app_storage.external.stories.StoriesExternalStorageService
import com.saver.igv1.business.data.network.downloadService.MediaDownloadService
import com.saver.igv1.business.data.network.storiesService.StoriesService
import com.saver.igv1.business.data.prefs.SharedPrefRepository
import com.saver.igv1.business.domain.DataState
import com.saver.igv1.business.domain.DownloadingLocationOptions
import com.saver.igv1.business.domain.ModalBottomSheetInfo
import com.saver.igv1.business.domain.UIComponent
import com.saver.igv1.business.domain.models.player.PlayerMediaItemInfo
import com.saver.igv1.business.interactors.stories.FetchStoryMediaData
import com.saver.igv1.business.interactors.stories.GetStoriesMediaData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SingleTrayPreviewViewModel(
    private val fetchStoryMediaData: FetchStoryMediaData,
    private val getStoriesMediaData: GetStoriesMediaData,
    private val savedStateHandle: SavedStateHandle,
    private val mediaDownloadService: MediaDownloadService,
    private val storiesService: StoriesService,
    private val sharedPrefRepository: SharedPrefRepository,
    private val storiesExternalStorageService: StoriesExternalStorageService
) : ViewModel() {

    val state = MutableStateFlow(SingleTrayPreviewState())

    init {
        viewModelScope.launch {
            getStoriesMediaData.invoke().collect { storyList ->
                storyList.filter { it.user?.username == savedStateHandle["userName"] }.let {
                    state.value = state.value.copy(storyMediaData = it)
                }
            }
        }
    }

    fun onEvent(storiesTrayEvents: SingleTrayPreviewEvents) {
        when (storiesTrayEvents) {
            is SingleTrayPreviewEvents.InitStoryMediaData -> {
                viewModelScope.launch {
                    fetchStoryMediaData(
                        savedStateHandle["trayItemPos"], storiesTrayEvents.reelMediaList
                    )
                }
            }

            is SingleTrayPreviewEvents.UpdateActiveModalBottomSheetInfo -> {
                updateActiveModalBottomSheetInfo(storiesTrayEvents)
            }

            is SingleTrayPreviewEvents.StartDownloadingSingleStory -> {
                startDownloadingSingleStory(storiesTrayEvents)
            }
        }
    }


    private fun updateActiveModalBottomSheetInfo(storiesTrayEvents: SingleTrayPreviewEvents.UpdateActiveModalBottomSheetInfo) {
        if (storiesTrayEvents.modalBottomSheetInfo is ModalBottomSheetInfo.SelectDownloadLocationBs) {
            if (sharedPrefRepository.get_DOWNLOADING_OPTION() == DownloadingLocationOptions.AskEveryTime) {
                state.value.updateActiveModalBottomSheetInfo(
                    ModalBottomSheetInfo.SelectDownloadLocationBs(
                        storiesTrayEvents.modalBottomSheetInfo.mediaDownloadOptions,
                        storyMediaInfo = storiesTrayEvents.modalBottomSheetInfo.storyMediaInfo
                    ) {
                        if (it.notAskAgainSelected) {
                            sharedPrefRepository.set_DOWNLOADING_OPTION(it.downloadingOption)
                        }
                        onEvent(
                            SingleTrayPreviewEvents.StartDownloadingSingleStory(
                                downloadingLocationOptions = it.downloadingOption,
                                mediaDownloadOptions = storiesTrayEvents.modalBottomSheetInfo.mediaDownloadOptions,
                                storyMediaInfo = storiesTrayEvents.modalBottomSheetInfo.storyMediaInfo
                            )
                        )
                    }
                )
            } else {
                onEvent(
                    SingleTrayPreviewEvents.StartDownloadingSingleStory(
                        downloadingLocationOptions = sharedPrefRepository.get_DOWNLOADING_OPTION(),
                        mediaDownloadOptions = storiesTrayEvents.modalBottomSheetInfo.mediaDownloadOptions,
                        storyMediaInfo = storiesTrayEvents.modalBottomSheetInfo.storyMediaInfo
                    )
                )
            }

        } else {
            state.value.updateActiveModalBottomSheetInfo(storiesTrayEvents.modalBottomSheetInfo)
        }
        storiesTrayEvents.list?.let {
            viewModelScope.launch {
                (if ((storiesTrayEvents.list.value.size) > 3)
                    storiesTrayEvents.list.value.subList(0, 3) else
                    storiesTrayEvents.list.value).forEachIndexed { i, it ->
                    it.url?.let { url ->
                        val size = storiesService.getStoriesFileSize(url)
                        storiesTrayEvents.list.value =
                            storiesTrayEvents.list.value.mapIndexed { index, storyDownloadOptions ->
                                if (index == i)
                                    storyDownloadOptions.copy(size = "${size}MB") else storyDownloadOptions.copy()
                            }
                    }
                }
            }
        }
    }

    private fun startDownloadingSingleStory(storiesTrayEvents: SingleTrayPreviewEvents.StartDownloadingSingleStory) {
        viewModelScope.launch {
            storiesTrayEvents.mediaDownloadOptions.url?.let {
                state.value.updateUiComponent(
                    UIComponent.DownloadingSingleMediaDialog(
                        0f,
                        storiesTrayEvents.downloadingLocationOptions
                    )
                )
                mediaDownloadService.downloadMediaFile(it).collect {
                    if (it is DataState.Response && it.uiComponent is UIComponent.DownloadingSingleMediaDialog) {
                        state.value.updateUiComponent(
                            UIComponent.DownloadingSingleMediaDialog(
                                it.uiComponent.progress,
                                storiesTrayEvents.downloadingLocationOptions
                            )
                        )
                    } else if (it is DataState.Data) {
                        it.data?.let { byteArray ->
                            storiesExternalStorageService.saveStory(
                                storyMediaInfo = storiesTrayEvents.storyMediaInfo,
                                byteArray = byteArray
                            ).collect{
                                println("677688 Saved Media Uri: $it")
                            }
                        }

                        state.value.updateUiComponent(UIComponent.None)
                    }
                }
            }
        }
    }

    fun getPlayerMediaItemInfoList(userName: String) {
        viewModelScope.launch {
            getStoriesMediaData.invoke().collect { storyList ->
                storyList.filter { it.user?.username == userName }.let {
                    state.value = state.value.copy(playerMediaItemsData = it.map {
                        PlayerMediaItemInfo(
                            mediaUrl = it.videoMediaData?.firstOrNull()?.url
                                ?: it.imageMediaData?.firstOrNull()?.url,
                            thumbNailUrl = it.imageMediaData?.firstOrNull()?.url,
                            isVideo = it.isVideo,
                            mediaCreationTimeStamp = it.mediaTakenAtTimeStamp,
                            videoDuration = it.videoDuration,
                            id = it.id,
                            storyTouchInteractionsMetaData = it.storyInteractionsMetaData
                        )
                    })
                }
            }
        }
    }
}