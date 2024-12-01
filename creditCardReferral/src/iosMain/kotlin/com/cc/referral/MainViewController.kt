package com.cc.referral

import androidx.compose.ui.window.ComposeUIViewController
import com.cc.referral.di.initKoin
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App()
}