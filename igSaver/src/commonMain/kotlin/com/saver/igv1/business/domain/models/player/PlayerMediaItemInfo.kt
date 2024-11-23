package com.saver.igv1.business.domain.models.player

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.saver.igv1.business.domain.models.stories.StoryInteractionsMetaData

data class PlayerMediaItemInfo(
    val id: String?,
    val mediaUrl: String?,
    val thumbNailUrl: String?,
    val isVideo: Boolean?,
    val mediaCreationTimeStamp: Long?,
    val videoDuration: Double?,
    val storyTouchInteractionsMetaData: StoryInteractionsMetaData?
)
