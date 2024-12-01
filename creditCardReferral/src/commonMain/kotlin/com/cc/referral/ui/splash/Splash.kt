package com.cc.referral.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.cc.referral.AppColors
import creditcardreferral.creditcardreferral.generated.resources.Res
import creditcardreferral.creditcardreferral.generated.resources.ic_splash_icon
import org.jetbrains.compose.resources.painterResource


@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier.fillMaxSize().background(AppColors.lightGreen),
        contentAlignment = Alignment.Center
    ) {

        Image(
            painter = painterResource(resource = Res.drawable.ic_splash_icon), null
        )
    }
}