package com.cc.referral.ui.onboarding.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cc.referral.AppColors
import com.cc.referral.ui.components.GenericRoundedCornerTextComponent

@Composable
fun EnterOTPBs(
    phoneNumber: String,
    otpInput: MutableState<String>,
    onVerifyClicked: (otp: String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().padding(20.dp)) {

        Surface(
            shape = RoundedCornerShape(2.dp),
            color = AppColors.darkGreen,
            modifier = Modifier.align(Alignment.CenterHorizontally).height(4.dp).width(65.dp)
        ) {}

        Spacer(modifier = Modifier.height(35.dp))

        Text(
            "Verify Mobile Number",
            color = AppColors.black,
            fontSize = 28.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            "We have send the OTP to $phoneNumber via SMS",
            color = AppColors.black,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(38.dp))

        BasicTextField(otpInput.value,
            onValueChange = {
                if (it.lastOrNull()
                        ?.isDigit() == false && it.length > otpInput.value.length
                ) return@BasicTextField
                otpInput.value = it
            },
            cursorBrush = SolidColor(AppColors.green15320077),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            decorationBox = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    repeat(6) {
                        val char = when {
                            it >= otpInput.value.length -> ""
                            else -> otpInput.value[it].toString()
                        }

                        val isFocused = it == otpInput.value.length

                        Surface(
                            modifier =
                            Modifier
                                .size(45.dp)
                                .align(Alignment.CenterVertically),
                            border = BorderStroke(
                                if (isFocused) 2.dp else 1.dp,
                                AppColors.green15320077
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = char,
                                    color = AppColors.black,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    textAlign = TextAlign.Center,
                                )
                            }

                        }
                        Spacer(modifier = Modifier.width(10.dp))
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            "Resend OTP",
            color = AppColors.black,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(38.dp))

        GenericRoundedCornerTextComponent(
            text = "Verify Mobile",
        ) {
            if (otpInput.value.length == 6) {
                onVerifyClicked(otpInput.value)
            }
        }
    }
}