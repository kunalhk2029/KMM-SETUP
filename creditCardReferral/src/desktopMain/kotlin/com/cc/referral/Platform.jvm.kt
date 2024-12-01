package com.cc.referral

import com.cc.referral.business.domain.PlatformInfo


actual fun getPlatform(): PlatformInfo =
    PlatformInfo.Desktop("Java ${System.getProperty("java.version")}")
