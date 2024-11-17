package com.saver.igv1

import android.os.Build
import com.saver.igv1.business.data.prefs.SharedPrefRepository
import com.saver.igv1.business.domain.PlatformInfo


actual fun getPlatform(): PlatformInfo = PlatformInfo.Android(Build.VERSION.SDK_INT)

actual object AndroidVersion {

    actual fun isVersionAbove29(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
    }

    actual fun isVersionAbove33(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
    }
}

actual class WebView {
    actual fun load(
        url: String,
        sharedPrefRepository: SharedPrefRepository
    ) {
    }
}