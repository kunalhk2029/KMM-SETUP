package com.cc.referral

import com.cc.referral.business.domain.PlatformInfo


expect fun getPlatform(): PlatformInfo


expect object AndroidVersion {

    fun isVersionAbove29(): Boolean

    fun isVersionAbove33(): Boolean
}

expect fun getCurrentTimeInMillis(): Long



interface AndroidPlatformSpecificMethods {
}

