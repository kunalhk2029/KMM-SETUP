package com.saver.igv1.ui.main.common.nav_drawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import instagramsaverv1.igsaver.generated.resources.Res
import instagramsaverv1.igsaver.generated.resources.ic_launcher
import instagramsaverv1.igsaver.generated.resources.madeinindia
import instagramsaverv1.igsaver.generated.resources.marketing_promo
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


@Composable
fun NavDrawerHeader() {

    Column(
        modifier = Modifier.fillMaxWidth().background(
            color = com.saver.igv1.Colors.purple
        ), horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = stringResource(Res.string.madeinindia),
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(15.dp)
        )

        Image(
            painter = painterResource(Res.drawable.ic_launcher),
            contentDescription = null,
            modifier = Modifier.size(80.dp)
        )

        Text(
            text = stringResource(Res.string.marketing_promo),
            color = Color.White,
            fontSize = 12.5.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(15.dp)
        )

    }
}