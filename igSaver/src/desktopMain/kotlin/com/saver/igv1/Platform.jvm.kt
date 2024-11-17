package com.saver.igv1

import com.saver.igv1.business.domain.PlatformInfo


actual fun getPlatform(): PlatformInfo =
    PlatformInfo.Desktop("Java ${System.getProperty("java.version")}")
