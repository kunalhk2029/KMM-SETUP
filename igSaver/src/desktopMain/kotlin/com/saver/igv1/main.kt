package com.saver.igv1

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.saver.igv1.di.initKoin

fun main() {

    initKoin()

    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "InstagramSaverV1",
        ) {
            App()
        }
    }
}