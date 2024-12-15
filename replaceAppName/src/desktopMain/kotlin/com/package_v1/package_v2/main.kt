package com.package_v1.package_v2

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.package_v1.package_v2.di.initKoin

fun main() {

    initKoin()

    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "ReplaceAppNameV1",
        ) {
            App()
        }
    }
}