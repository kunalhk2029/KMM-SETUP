package com.cc.referral.ui.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cc.referral.AppColors
import com.cc.referral.ui.components.GenericRoundedCornerTextComponent
import creditcardreferral.creditcardreferral.generated.resources.Res
import creditcardreferral.creditcardreferral.generated.resources.ic_app_overview_bg
import creditcardreferral.creditcardreferral.generated.resources.ic_splash_icon
import org.jetbrains.compose.resources.painterResource


@Composable
fun AppOverviewScreen(
    onGetStartedClicked: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize().background(AppColors.lightGreen),
        contentAlignment = Alignment.Center
    ) {

        Image(
            painter = painterResource(resource = Res.drawable.ic_app_overview_bg), null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        LinearProgressIndicator(
            progress = 0.2f,
            color = AppColors.darkGreen,
            backgroundColor = AppColors.whiteECEEEA,
            modifier = Modifier.fillMaxWidth().align(Alignment.TopCenter)
                .padding(horizontal = 25.dp).padding(top = 40.dp)
        )

        Column(
            modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter)
                .padding(bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MessageComponent(
                onGetStartedClicked = onGetStartedClicked
            )
        }
    }
}


@Composable
private fun MessageComponent(
    onGetStartedClicked: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 25.dp)) {

        Text(
            "GET A CREDIT CARD AND UNLOCK BIG REWARDS!", color = AppColors.white,
            fontSize = 40.sp,
            fontWeight = FontWeight.Normal,
            lineHeight = 42.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(13.dp))

        Text(
            "Process your own or your loved ones’ credit cards and earn up to ₹1000 in instant cash!",
            color = AppColors.white,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(30.dp))

        GenericRoundedCornerTextComponent(
            text = "Get Started"
        ) {
            onGetStartedClicked()
        }

    }
}