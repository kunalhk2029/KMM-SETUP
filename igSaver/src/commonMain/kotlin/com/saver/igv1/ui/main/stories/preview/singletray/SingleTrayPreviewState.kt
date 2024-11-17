package com.saver.igv1.ui.main.stories.preview.singletray

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.saver.igv1.business.domain.ModalBottomSheetInfo
import com.saver.igv1.business.domain.UIComponent
import com.saver.igv1.business.domain.ViewStateExt
import com.saver.igv1.business.domain.models.player.PlayerMediaItemInfo
import com.saver.igv1.business.domain.models.stories.StoryMediaInfo
import kotlinx.coroutines.flow.MutableStateFlow

data class SingleTrayPreviewState(
    val storyMediaData: List<StoryMediaInfo>? = null,
    val playerMediaItemsData: List<PlayerMediaItemInfo>? = null,
    val modalBottomSheetInfo: ModalBottomSheetInfo = ModalBottomSheetInfo.None
) : ViewStateExt()