package com.package_v1.package_v2

import com.package_v1.package_v2.business.domain.PlatformInfo


expect fun getPlatform(): PlatformInfo


expect object AndroidVersion {

    fun isVersionAbove29(): Boolean

    fun isVersionAbove33(): Boolean
}

expect fun getCurrentTimeInMillis(): Long



interface AndroidPlatformSpecificMethods {
}

