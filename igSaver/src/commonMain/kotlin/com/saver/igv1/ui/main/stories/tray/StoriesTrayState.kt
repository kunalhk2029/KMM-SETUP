package com.saver.igv1.ui.main.stories.tray

import com.saver.igv1.business.domain.ViewStateExt
import com.saver.igv1.business.domain.models.stories.StoriesTrayInfo


data class StoriesTrayState(
    var trayInfo: List<StoriesTrayInfo>? = null,
) : ViewStateExt()
