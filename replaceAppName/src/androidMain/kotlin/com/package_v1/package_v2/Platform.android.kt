package com.package_v1.package_v2

import android.os.Build
import com.package_v1.package_v2.business.domain.PlatformInfo


actual fun getPlatform(): PlatformInfo = PlatformInfo.Android(Build.VERSION.SDK_INT)

actual object AndroidVersion {

    actual fun isVersionAbove29(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
    }

    actual fun isVersionAbove33(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
    }
}



actual fun getCurrentTimeInMillis(): Long {
    return System.currentTimeMillis()
}