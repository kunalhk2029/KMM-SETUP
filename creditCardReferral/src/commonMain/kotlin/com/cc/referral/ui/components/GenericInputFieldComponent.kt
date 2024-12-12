package com.cc.referral.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cc.referral.AppColors
import creditcardreferral.creditcardreferral.generated.resources.Res
import creditcardreferral.creditcardreferral.generated.resources.ic_flag_india
import org.jetbrains.compose.resources.painterResource


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GenericInputFieldComponent(
    headerTitle: AnnotatedString,
    hintText: String? = null,
    inputText: MutableState<String?>,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Done
    ),
    maxInputTextFieldLines: Int? = null,
    modalBottomSheetState: ModalBottomSheetState? = null,
    headerFontSize: TextUnit = 12.sp,
    spacerHeight: Dp = 5.dp,
    headerFontWeight: FontWeight = FontWeight.Medium,
    modifier: Modifier = Modifier,
    characterLimit: Int? = null,
    onUpdatedInputReceived: (String) -> Unit = {},
    isFlagVisible: Boolean = false
) {


    Column(modifier = modifier.fillMaxWidth()) {

        Text(
            text = headerTitle,
            fontSize = headerFontSize,
            fontWeight = headerFontWeight,
            color = AppColors.black
        )

        Spacer(modifier = Modifier.height(spacerHeight))

        Surface(
            modifier = Modifier,
            shape = RoundedCornerShape(6.dp),
            border = BorderStroke(
                0.8.dp,
                AppColors.green15320077
            )
        ) {
            BasicTextField(
                modifier = Modifier
                    .padding(start = 11.dp, top = 11.dp, bottom = 11.dp, end = 15.dp),
                value = inputText.value ?: "",
                onValueChange = { updatedText ->
                    if (characterLimit != null && updatedText.length > characterLimit) return@BasicTextField
                    inputText.value = updatedText
                    onUpdatedInputReceived(updatedText)
                },
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = AppColors.greyEFEFEF
                ),
                decorationBox = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        if (isFlagVisible) {

                            Image(
                                painter = painterResource(
                                    Res.drawable.ic_flag_india
                                ),
                                null,
                                modifier = Modifier.height(20.dp),
                                contentScale = androidx.compose.ui.layout.ContentScale.Fit,
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            Text(
                                text = "+91 -",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = AppColors.black
                            )

                            Spacer(modifier = Modifier.width(5.dp))
                        }
                        Box {

                            it()

                            if (inputText.value.isNullOrEmpty()) {
                                hintText?.let { it1 ->
                                    Text(
                                        text = it1,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = AppColors.greyEFEFEF,
                                        maxLines = maxInputTextFieldLines ?: Int.MAX_VALUE
                                    )
                                }
                            }
                        }
                    }
                },
                cursorBrush = SolidColor(AppColors.green15320077),
                keyboardOptions = keyboardOptions,
                enabled = if (modalBottomSheetState != null)
                    !modalBottomSheetState.isVisible else true
            )
        }
    }
}