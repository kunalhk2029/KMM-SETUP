package com.cc.referral.ui.onboarding.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cc.referral.AppColors
import com.cc.referral.ui.components.GenericInputFieldComponent
import com.cc.referral.ui.components.GenericRoundedCornerTextComponent
import com.cc.referral.ui.components.WhatsAppUpdateToggleComponent
import creditcardreferral.creditcardreferral.generated.resources.Res
import creditcardreferral.creditcardreferral.generated.resources.ic_app_branding
import creditcardreferral.creditcardreferral.generated.resources.ic_app_overview_bg
import creditcardreferral.creditcardreferral.generated.resources.ic_splash_icon
import org.jetbrains.compose.resources.painterResource


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LoginScreen(
    state: LoginState,
    onEvents: (LoginEvents) -> Unit,
    onNavEvents: (LoginNavEvents) -> Unit
) {


    Column(
        modifier = Modifier.fillMaxSize().background(AppColors.white)
            .padding(horizontal = 25.dp)
            .padding(top = 40.dp)

    ) {

        Image(
            painterResource(resource = Res.drawable.ic_app_branding), null,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )

        Spacer(modifier = Modifier.height(55.dp))

        Text(
            "Log in to your account",
            color = AppColors.black,
            fontSize = 28.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )


        Spacer(modifier = Modifier.height(30.dp))

        GenericInputFieldComponent(
            headerTitle = AnnotatedString(""),
            inputText = state.mobileNumber,
            hintText = "Enter mobile number",
            isFlagVisible = true
        )

        Spacer(modifier = Modifier.height(30.dp))

        GenericRoundedCornerTextComponent(
            text = "Log in with OTP",
            textSize = 16.sp
        ) {

        }

        Spacer(modifier = Modifier.height(35.dp))

        WhatsAppUpdateToggleComponent(
            modifier = Modifier.fillMaxWidth()
        ){
            onEvents(LoginEvents.UpdateWhatsAppUpdatesToggleState(it))
        }

        Spacer(modifier = Modifier.weight(1f)) // Takes up all available space

        Text(
            "By continuing, you agree with our Privacy Policy, Credit Report Terms of Use and Terms of Use",
            color = AppColors.black,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 35.dp),
            lineHeight = 14.sp
        )

    }
}