package com.saver.igv1.ui.main.stories.preview.multipletray

import com.saver.igv1.business.domain.ModalBottomSheetInfo
import com.saver.igv1.business.domain.models.stories.StoriesTrayInfo

sealed class MultipleTrayPreviewEvents {

    data class UpdateTrayInfoList(
        var list: List<StoriesTrayInfo>? = null,
        val reelMediaList: MutableList<String>
    ) : MultipleTrayPreviewEvents()

    data class UpdateCurrentTrayItemPosition(
        val position: Int? = null,
    ) : MultipleTrayPreviewEvents()

    data class InitMediaPlayerItemsData(
        val position: Int
    ) : MultipleTrayPreviewEvents()

    data class UpdateMediaItemProgressData(
        val mediaListPosition: Int,
        val mediaListItemPosition: Int,
        val progress: Float
    ) : MultipleTrayPreviewEvents()

    data class UpdateActiveModalBottomSheetInfo(
        val modalBottomSheetInfo: ModalBottomSheetInfo?
    ) : MultipleTrayPreviewEvents()

}


sealed class MultipleTrayPreviewNavEvents {

}

