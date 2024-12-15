package com.package_v1.package_v2

import com.package_v1.package_v2.business.domain.PlatformInfo


actual fun getPlatform(): PlatformInfo =
    PlatformInfo.Desktop("Java ${System.getProperty("java.version")}")
