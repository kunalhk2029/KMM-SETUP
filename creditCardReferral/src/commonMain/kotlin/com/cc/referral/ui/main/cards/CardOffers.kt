package com.cc.referral.ui.main.cards

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cc.referral.AppColors
import com.cc.referral.ui.components.GenericRoundedCornerTextComponent
import com.cc.referral.ui.main.cards.components.CardBrandingComponent
import com.cc.referral.ui.main.cards.components.InvitationComponent
import com.cc.referral.ui.main.common.utils.ModifierExtensionUtils.clickable
import com.cc.referral.ui.onboarding.auth.AuthNavEvents


@Composable
fun CardOffers() {


    Column(modifier = Modifier.fillMaxWidth()) {

        InvitationComponent()

        var w  = remember { mutableStateOf(0f) }

        Column(modifier = Modifier.padding(20.dp)) {

            Text(
                text = "23 Card Offers from 9 Banks",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = AppColors.darkGreen,
                modifier = Modifier.onGloballyPositioned {
                    w.value = it.size.width.toFloat()
                }
            )

            Surface(
                color = AppColors.lightGreen,
                shape = RoundedCornerShape(11.dp),
                modifier = Modifier.width(165.dp).height(2.dp)
            ) {}
        }



        LazyColumn(modifier = Modifier.background(AppColors.greyF4F4F4)) {

            items(10) {

                Surface(
                    modifier = Modifier.fillMaxWidth().padding(20.dp),
                    shape = RoundedCornerShape(9.dp),
                    border = BorderStroke(1.dp, AppColors.whiteECEEEA)
                ) {

                    Column {
                        Surface(
                            modifier = Modifier.wrapContentWidth().align(Alignment.Start),
                            shape = RoundedCornerShape(topStart = 9.dp, bottomEnd = 9.dp),
                            color = AppColors.blue
                        ) {

                            Text(
                                "Earn upto ₹ 1,000",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Normal,
                                color = AppColors.darkGreen,
                                modifier = Modifier.padding(horizontal = 25.dp, vertical = 11.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(23.dp))

                        Text(
                            text = "Axis Bank Ace Visa Credit Card",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            color = AppColors.darkGreen,
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(13.dp))

                        CardBrandingComponent(modifier = Modifier.align(Alignment.CenterHorizontally))

                        Spacer(modifier = Modifier.height(21.dp))


                        Text(
                            text = "Earnings upto 37,605 every year \n5% cashback on bill payments & recharges\nEarn 4% cashback on popular brands\nEarn 4% cashback on popular brands",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            lineHeight = 13.sp,
                            color = AppColors.darkGreen,
                            modifier = Modifier.padding(start = 35.dp)
                        )

                        Spacer(modifier = Modifier.height(18.dp))

                        Text(
                            text = "More Details",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            lineHeight = 13.sp,
                            color = AppColors.green21DD08,
                            modifier = Modifier.padding(start = 35.dp)
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Row(
                            modifier =
                            Modifier.fillMaxWidth().padding(horizontal = 25.dp)
                                .padding(bottom = 20.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {

                            GenericRoundedCornerTextComponent(
                                text = "Share Link",
                                modifier = Modifier.weight(0.5f),
                                textSize = 12.sp,
                                color = AppColors.white,
                                textColor = AppColors.darkGreen,
                                borderStroke = BorderStroke(1.dp, AppColors.green85947B),
                                shape = RoundedCornerShape(21.dp),
                                textPadding = 10.dp
                            ) {
                            }

                            Spacer(modifier = Modifier.width(25.dp))

                            GenericRoundedCornerTextComponent(
                                text = "Apply Now",
                                modifier = Modifier.weight(0.5f),
                                textSize = 12.sp,
                                shape = RoundedCornerShape(21.dp),
                                borderStroke = BorderStroke(1.dp, AppColors.lightGreen),
                                textPadding = 10.dp
                            ) {
                            }
                        }

                    }
                }
            }
        }
    }
}