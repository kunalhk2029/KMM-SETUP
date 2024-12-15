package com.package_v1.package_v2

import com.package_v1.package_v2.business.domain.PlatformInfo
import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIDevice


@OptIn(ExperimentalForeignApi::class)
actual fun getPlatform(): PlatformInfo =
    PlatformInfo.IOS(UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion)

