package com.cc.referral

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.cc.referral.di.initKoin

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