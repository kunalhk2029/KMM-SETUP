package com.cc.referral.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import creditcardreferral.creditcardreferral.generated.resources.Res
import creditcardreferral.creditcardreferral.generated.resources.ic_circular_check_icon
import creditcardreferral.creditcardreferral.generated.resources.ic_circular_unchecked_icon
import org.jetbrains.compose.resources.painterResource


@Composable
fun GenericCircularCheckBox(
    isChecked: MutableState<Boolean>,
    isEnabled: MutableState<Boolean> = mutableStateOf(true),
    size: Dp = 20.dp,
    onClicked: () -> Unit = {}
) {

    val interactionSource = remember { MutableInteractionSource() }

    Surface(
        shape = CircleShape,
        modifier = Modifier.clickable(interactionSource, null) {
            if (isEnabled.value) {
                onClicked()
            }
        }.size(size)
    ) {
        if (isChecked.value) {
            Image(
                painter = painterResource(Res.drawable.ic_circular_check_icon),
                null
            )
        } else {
            Image(
                painter = painterResource(Res.drawable.ic_circular_unchecked_icon),
                null
            )
        }
    }
}