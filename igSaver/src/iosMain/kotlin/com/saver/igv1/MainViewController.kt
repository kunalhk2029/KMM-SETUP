package com.saver.igv1

import androidx.compose.ui.window.ComposeUIViewController
import com.saver.igv1.di.initKoin
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App()
}