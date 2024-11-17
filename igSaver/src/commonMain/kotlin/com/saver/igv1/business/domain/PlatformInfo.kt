package com.saver.igv1.business.domain


sealed class PlatformInfo {
    data class Android(val versionCode: Int? = null) : PlatformInfo()
    data class IOS(val version: String? = null) : PlatformInfo()
    data class Desktop(val version: String? = null) : PlatformInfo()
    data object Web : PlatformInfo()
}