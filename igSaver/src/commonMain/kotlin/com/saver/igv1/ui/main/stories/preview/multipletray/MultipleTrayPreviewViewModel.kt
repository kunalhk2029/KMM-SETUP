package com.saver.igv1.ui.main.stories.preview.multipletray

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saver.igv1.business.domain.models.player.PlayerMediaItemInfo
import com.saver.igv1.business.interactors.stories.GetStoriesMediaData
import com.saver.igv1.business.interactors.stories.FetchStoryMediaData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


class MultipleTrayPreviewViewModel(
    private val fetchStoryMediaData: FetchStoryMediaData,
    private val getStoriesMediaData: GetStoriesMediaData,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val state = mutableStateOf(
        MultipleTrayPreviewState(
            currentTrayItemPos = savedStateHandle.get<Int>("trayItemPos")
        )
    )

    init {
        viewModelScope.launch {
            getStoriesMediaData.invoke().onEach { storyList ->
                state.value.trayInfo?.map { it.user?.username }?.forEachIndexed { index, username ->
                    if (state.value.storyMediaItemsData.value[username] == null) {
                        val updatedStoryMediaItemsData = state.value.storyMediaItemsData.value
                        updatedStoryMediaItemsData[username] =
                            Pair(index,
                                storyList.filter {
                                    it.user?.username == username
                                }.sortedBy { it.mediaTakenAtTimeStamp }.map {
                                    PlayerMediaItemInfo(
                                        mediaUrl = it.videoMediaData?.firstOrNull()?.url
                                            ?: it.imageMediaData?.firstOrNull()?.url,
                                        isVideo = it.isVideo,
                                        mediaCreationTimeStamp = it.mediaTakenAtTimeStamp,
                                        videoDuration = it.videoDuration,
                                        id = it.id,
                                        storyTouchInteractionsMetaData = it.storyInteractionsMetaData
                                    )
                                })

                        state.value = state.value.copy(
                            storyMediaItemsData = mutableStateOf(updatedStoryMediaItemsData)
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun onEvent(multipleTrayPreviewEvents: MultipleTrayPreviewEvents) {
        when (multipleTrayPreviewEvents) {

            is MultipleTrayPreviewEvents.UpdateCurrentTrayItemPosition -> {
                state.value = state.value.copy(
                    currentTrayItemPos = multipleTrayPreviewEvents.position
                )
            }

            is MultipleTrayPreviewEvents.UpdateTrayInfoList -> {
                state.value = state.value.copy(
                    trayInfo = multipleTrayPreviewEvents.list,
                    reelMediaList = multipleTrayPreviewEvents.reelMediaList
                )
            }

            is MultipleTrayPreviewEvents.InitMediaPlayerItemsData -> {
                viewModelScope.launch {
                    fetchStoryMediaData(
                        multipleTrayPreviewEvents.position, state.value.reelMediaList
                    )
                }
            }

            is MultipleTrayPreviewEvents.UpdateActiveModalBottomSheetInfo -> {
                state.value.updateActiveModalBottomSheetInfo(multipleTrayPreviewEvents.modalBottomSheetInfo)
            }

            is MultipleTrayPreviewEvents.UpdateMediaItemProgressData -> {

                val storyMediaItemsProgressData = state.value.storyMediaItemsProgressData.value

                storyMediaItemsProgressData[multipleTrayPreviewEvents.mediaListPosition] = Pair(
                    multipleTrayPreviewEvents.mediaListItemPosition,
                    multipleTrayPreviewEvents.progress
                )

                state.value = state.value.copy(
                    storyMediaItemsProgressData = MutableStateFlow(storyMediaItemsProgressData)
                )
            }

            else -> {}
        }
    }
}