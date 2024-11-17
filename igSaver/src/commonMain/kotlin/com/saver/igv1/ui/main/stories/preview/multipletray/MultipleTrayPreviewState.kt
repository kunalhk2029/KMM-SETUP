package com.saver.igv1.ui.main.stories.preview.multipletray

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.saver.igv1.business.domain.ModalBottomSheetInfo
import com.saver.igv1.business.domain.ViewStateExt
import com.saver.igv1.business.domain.models.player.PlayerMediaItemInfo
import com.saver.igv1.business.domain.models.stories.StoriesTrayInfo
import kotlinx.coroutines.flow.MutableStateFlow

data class MultipleTrayPreviewState(
    var trayInfo: List<StoriesTrayInfo>? = null,
    val reelMediaList: MutableList<String> = mutableListOf(),
    var currentTrayItemPos: Int? = null,
    val storyMediaItemsData: MutableState<HashMap<String?, Pair<Int, List<PlayerMediaItemInfo>?>>> = mutableStateOf(
        HashMap()
    ),
    val storyMediaItemsProgressData: MutableStateFlow<HashMap<Int?, Pair<Int?, Float>?>> = MutableStateFlow(
        HashMap()
    )
) : ViewStateExt()