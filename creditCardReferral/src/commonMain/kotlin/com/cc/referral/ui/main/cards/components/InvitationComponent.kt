package com.cc.referral.ui.main.cards.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cc.referral.AppColors


@Composable
fun InvitationComponent() {
    Column(
        modifier = Modifier.fillMaxWidth().background(AppColors.lightGreen)
            .padding(20.dp)
    ) {

        Text(
            text = "INVITE FRIENDS TO GET ₹1000",
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal,
            color = AppColors.darkGreen,
            modifier = Modifier
        )

        Spacer(modifier = Modifier.height(7.dp))

        Text(
            text = "Invite friends to Finance Buddy’s and get ₹1000 when your friend Get a credit card. They get ₹200!",
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            lineHeight = 13.sp,
            color = AppColors.green15320061,
            modifier = Modifier.padding(bottom = 30.dp)
        )
    }
}