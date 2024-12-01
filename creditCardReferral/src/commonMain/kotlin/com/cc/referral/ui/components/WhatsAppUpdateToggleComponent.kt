package com.cc.referral.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.IconToggleButton
import androidx.compose.material.Switch
import androidx.compose.material.SwitchColors
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cc.referral.AppColors
import creditcardreferral.creditcardreferral.generated.resources.Res
import creditcardreferral.creditcardreferral.generated.resources.ic_whatsapp_logo
import org.jetbrains.compose.resources.painterResource


@Composable
fun WhatsAppUpdateToggleComponent(
    modifier: Modifier = Modifier,
    onCheckedChange: (Boolean) -> Unit
) {

    val isWhatsAppUpdateEnabled = remember { mutableStateOf(false) }

    Row(
        modifier = modifier, verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        Image(
            painter = painterResource(Res.drawable.ic_whatsapp_logo),
            null,
            modifier = Modifier.size(30.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = "Get updates on WhatsApp",
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = AppColors.black,
        )

        Spacer(modifier = Modifier.width(9.dp))

        Switch(
            checked = isWhatsAppUpdateEnabled.value,
            onCheckedChange = {
                isWhatsAppUpdateEnabled.value = it
                onCheckedChange(it)
            },
            modifier = Modifier.width(32.dp).height(20.dp),
            colors = SwitchDefaults.colors(
                checkedThumbColor = AppColors.white,
                checkedTrackColor = AppColors.black,
                uncheckedThumbColor = AppColors.white,
                uncheckedTrackColor = AppColors.black
            ),
        )
    }
}