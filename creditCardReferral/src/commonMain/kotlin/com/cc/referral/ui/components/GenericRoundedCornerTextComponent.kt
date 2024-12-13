package com.cc.referral.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cc.referral.AppColors
import com.cc.referral.ui.main.common.utils.ModifierExtensionUtils.clickable


@Composable
fun GenericRoundedCornerTextComponent(
    color: Color = AppColors.lightGreen,
    shape: RoundedCornerShape = RoundedCornerShape(29.dp),
    text: String,
    textSize: TextUnit = 18.sp,
    modifier: Modifier = Modifier.fillMaxWidth().padding(2.dp),
    textColor: Color = AppColors.black,
    borderStroke: BorderStroke? = null,
    textPadding: Dp = 13.dp,
    onClick: () -> Unit = {},
) {

    Surface(
        color = color,
        shape = shape,
        border = borderStroke,
        modifier = modifier.clickable() {
            onClick()
        }) {
        Text(
            text = text,
            color = textColor,
            modifier = Modifier.padding(textPadding),
            textAlign = TextAlign.Center,
            fontSize = textSize,
            fontWeight = FontWeight.SemiBold
        )
    }
}