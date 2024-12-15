package com.package_v1.package_v2

import androidx.compose.ui.window.ComposeUIViewController
import com.package_v1.package_v2.di.initKoin
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App()
}