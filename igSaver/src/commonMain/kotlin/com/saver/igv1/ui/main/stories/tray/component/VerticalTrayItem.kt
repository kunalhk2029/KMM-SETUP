package com.saver.igv1.ui.main.stories.tray.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.saver.igv1.business.domain.models.stories.StoriesTrayInfo

@Composable
fun VerticalTrayItem(
    storiesTrayInfo: StoriesTrayInfo,
    onItemClicked: () -> Unit = {}
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().wrapContentHeight().clickable {
            onItemClicked()
        }.padding(20.dp)
    ) {

        AsyncImage(
            model = storiesTrayInfo.user?.profile_pic_url,
            contentDescription = null,
            modifier = Modifier.clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(15.dp))

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {

            storiesTrayInfo.user?.full_name?.let {
                Text(
                    text = it,
                    fontSize = 16.sp,
                )
            }

            storiesTrayInfo.user?.username?.let {
                Text(
                    text = it,
                    fontSize = 14.sp,
                )
            }
        }
    }
}