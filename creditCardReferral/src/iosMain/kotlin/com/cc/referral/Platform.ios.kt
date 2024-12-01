package com.cc.referral

import com.cc.referral.business.domain.PlatformInfo
import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIDevice


@OptIn(ExperimentalForeignApi::class)
actual fun getPlatform(): PlatformInfo =
    PlatformInfo.IOS(UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion)

