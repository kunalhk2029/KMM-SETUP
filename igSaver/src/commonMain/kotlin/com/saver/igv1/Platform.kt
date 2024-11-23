package com.saver.igv1

import com.saver.igv1.business.data.prefs.SharedPrefRepository
import com.saver.igv1.business.domain.PlatformInfo


expect fun getPlatform(): PlatformInfo


expect object AndroidVersion {

    fun isVersionAbove29(): Boolean

    fun isVersionAbove33(): Boolean
}

expect fun getCurrentTimeInMillis(): Long

expect class WebView() {
    fun load(url: String, sharedPrefRepository: SharedPrefRepository)
}

interface AndroidPlatformSpecificMethods {
    fun StartLoginActivity()
}

