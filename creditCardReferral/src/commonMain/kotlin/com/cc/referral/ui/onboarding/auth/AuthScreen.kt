package com.cc.referral.ui.onboarding.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cc.referral.AppColors
import com.cc.referral.ui.components.GenericRoundedCornerTextComponent


@Composable
fun AuthScreen(
    onNavEvents: (AuthNavEvents) -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize().background(AppColors.white),
        contentAlignment = Alignment.Center
    ) {
        val imageHeight = this.maxHeight * 0.3f

        Column(
            modifier = Modifier.fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(horizontal = 25.dp)
                .padding(top = 40.dp)

        ) {

            LinearProgressIndicator(
                progress = 100f,
                color = AppColors.darkGreen,
                backgroundColor = AppColors.whiteECEEEA,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(80.dp))

            Surface(
                color = AppColors.greyEFEFEF,
                modifier = Modifier.padding(horizontal = 25.dp)
                    .height(imageHeight)
                    .width(imageHeight).align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(37.dp)
            ) {

            }

            Spacer(modifier = Modifier.height(70.dp))

            MessageComponent(
                onNavEvents
            )

        }


        Column(
            modifier = Modifier.fillMaxWidth(),
//                .align(Alignment.BottomCenter)
//                .weight(0.5f)
//                .padding(bottom = 40.dp)
//            horizontalAlignment = Alignment.CenterHorizontally
        ) {
        }
    }
}


@Composable
private fun MessageComponent(onNavEvents: (AuthNavEvents) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 25.dp)) {

        Text(
            "ONE ACCOUNT FOR ALL YOUR NEEDS", color = AppColors.black,
            fontSize = 40.sp,
            fontWeight = FontWeight.Normal,
            lineHeight = 42.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(13.dp))

        Text(
            "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et",
            color = AppColors.black,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(50.dp))

        Row(
            modifier =
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {

            GenericRoundedCornerTextComponent(
                text = "Log in",
                modifier = Modifier.weight(0.5f),
                textSize = 16.sp
            ) {
                onNavEvents(AuthNavEvents.NavigateToLoginScreen)
            }

            Spacer(modifier = Modifier.width(25.dp))

            GenericRoundedCornerTextComponent(
                text = "Register",
                modifier = Modifier.weight(0.5f),
                textSize = 16.sp
            ) {
                onNavEvents(AuthNavEvents.NavigateToRegisterScreen)
            }
        }
    }
}