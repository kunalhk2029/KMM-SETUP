package com.package_v1.package_v2.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.package_v1.package_v2.AppColors
import org.jetbrains.compose.resources.painterResource
import replaceprojectname.replaceappname.generated.resources.Res
import replaceprojectname.replaceappname.generated.resources.compose_multiplatform


@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier.fillMaxSize().background(AppColors.lightGreen),
        contentAlignment = Alignment.Center
    ) {

        Image(
            painter = painterResource(resource = Res.drawable.compose_multiplatform), null
        )
    }
}