package com.saver.igv1.ui.ig_auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saver.igv1.Colors
import com.saver.igv1.WebView
import com.saver.igv1.business.data.prefs.SharedPrefRepository
import com.saver.igv1.business.domain.PlatformInfo
import com.saver.igv1.getPlatform
import instagramsaverv1.igsaver.generated.resources.Res
import instagramsaverv1.igsaver.generated.resources.ic_undraw_security_on_re_e491
import instagramsaverv1.igsaver.generated.resources.login
import instagramsaverv1.igsaver.generated.resources.the_feature_you_want_to_use_requires_instagram_log_in
import instagramsaverv1.igsaver.generated.resources.you_log_in_to_official_instagram_website_we_can_t_save_or_access_any_of_your_private_inforamtion_like_password_it_is_100_safe_and_secure
import instagramsaverv1.igsaver.generated.resources.your_security_is_our_no_1_priority
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject


@Composable
fun SecurityScreen(
    onLoginClicked: () -> Unit
) {

    val sharedPrefRepository: SharedPrefRepository = koinInject()

    Column(
        modifier = Modifier.fillMaxSize().background(Colors.blue).padding(20.dp)
    ) {

        Text(
            modifier = Modifier.fillMaxWidth().padding(top = 25.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            text = stringResource(Res.string.your_security_is_our_no_1_priority),
            color = Colors.white,
            textAlign = TextAlign.Center,
        )

        Text(
            modifier = Modifier.padding(top = 20.dp),
            fontSize = 14.sp,
            lineHeight = 18.sp,
            text = stringResource(Res.string.you_log_in_to_official_instagram_website_we_can_t_save_or_access_any_of_your_private_inforamtion_like_password_it_is_100_safe_and_secure),
            color = Colors.white,
            textAlign = TextAlign.Center
        )

        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(
                top = 15.dp
            ),
            onClick = {
                if (getPlatform() is PlatformInfo.IOS) {
                    WebView().load(
                        "https://www.instagram.com/accounts/login/",
                        sharedPrefRepository
                    )
                } else {
                    onLoginClicked.invoke()
                }
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Colors.white
            ),
        ) {
            Text(
                text = stringResource(Res.string.login), color = Colors.green
            )
        }

        Text(
            modifier = Modifier.padding(2.dp),
            fontSize = 12.sp,
            text = stringResource(Res.string.the_feature_you_want_to_use_requires_instagram_log_in),
            color = Colors.white,
        )

        Image(
            modifier = Modifier.weight(1f),
            painter = painterResource(Res.drawable.ic_undraw_security_on_re_e491),
            contentDescription = null
        )
    }

}