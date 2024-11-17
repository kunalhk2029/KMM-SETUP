package com.saver.igv1.ui.main.stories.tray

import com.saver.igv1.business.domain.models.stories.StoriesTrayInfo

sealed class StoriesTrayEvents {

    data class InitTrayData(val hitApi: Boolean) : StoriesTrayEvents()

}

sealed class StoriesTrayNavEvents {

    data class NavigateToSingleTrayPreview(val storiesTrayItem: StoriesTrayInfo) : StoriesTrayNavEvents()
    data class NavigateToMultipleTrayPreview(val storiesTrayItem: StoriesTrayInfo) : StoriesTrayNavEvents()
}