package com.cc.referral.ui.main.kyc

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cc.referral.AppColors
import com.cc.referral.ui.components.GenericInputFieldComponent
import com.cc.referral.ui.components.GenericRoundedCornerTextComponent
import com.cc.referral.ui.components.WhatsAppUpdateToggleComponent
import com.cc.referral.ui.onboarding.register.RegisterEvents
import creditcardreferral.creditcardreferral.generated.resources.Res
import creditcardreferral.creditcardreferral.generated.resources.ic_app_branding
import org.jetbrains.compose.resources.painterResource


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun KYCScreen1(state: KYCState) {

    Column(
        modifier = Modifier.fillMaxSize().background(AppColors.white)
            .padding(horizontal = 25.dp)
            .padding(top = 40.dp)

    ) {

        Image(
            painterResource(resource = Res.drawable.ic_app_branding), null,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )

        Spacer(modifier = Modifier.height(30.dp))

        Surface(
            modifier = Modifier.fillMaxWidth().height(150.dp),
            shape = RoundedCornerShape(11.dp),
            color = AppColors.greyEFEFEF,
        ) {

        }

        Spacer(modifier = Modifier.height(30.dp))

        GenericInputFieldComponent(
            headerTitle = AnnotatedString("Full Name"),
            inputText = state.name,
            hintText = "Enter name as per PAN Card",
        )

        Spacer(modifier = Modifier.height(30.dp))

        GenericInputFieldComponent(
            headerTitle = AnnotatedString("Email Address"),
            inputText = state.name,
            hintText = "Enter email address",
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Gender",
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = AppColors.black
        )

        Spacer(modifier = Modifier.height(15.dp))


        Row(verticalAlignment = Alignment.CenterVertically) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = state.isMale.value == true,
                    onClick = {
                        state.isMale.value = true
                    },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = AppColors.green21DD08,
                    )
                )
                Spacer(modifier = Modifier.width(7.dp))

                Text(
                    text = "Male",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = AppColors.black
                )
            }

            Spacer(modifier = Modifier.width(80.dp))


            Row(verticalAlignment = Alignment.CenterVertically) {

                RadioButton(
                    selected = state.isMale.value == false,
                    onClick = {
                        state.isMale.value = false
                    },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = AppColors.green21DD08,
                    )
                )

                Spacer(modifier = Modifier.width(7.dp))

                Text(
                    text = "Female",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = AppColors.black
                )
            }
        }
        Spacer(modifier = Modifier.height(40.dp))

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {

            Checkbox(
                checked = state.isTermsAndConditionsAccepted.value == true,
                onCheckedChange = {
                    state.isTermsAndConditionsAccepted.value = it
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = AppColors.green21DD08,
                )
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = "I have read and agree to Credit Score Terms of Use and hereby appoint Paisabazaar as my authorised representative to receive my credit information from Cibil / Equifax / Experian / CRIF Highmark (bureau).",
                fontSize = 12.sp,
                lineHeight = 13.sp,
                fontWeight = FontWeight.Medium,
                color = AppColors.darkGreen
            )

        }

        Spacer(modifier = Modifier.height(17.dp))

        GenericRoundedCornerTextComponent(
            text = "Continue",
            textSize = 16.sp
        ) {

        }

        Spacer(modifier = Modifier.height(35.dp))

        WhatsAppUpdateToggleComponent(
            modifier = Modifier.fillMaxWidth()
        ) {

        }

        Spacer(modifier = Modifier.height(35.dp))

    }
}

