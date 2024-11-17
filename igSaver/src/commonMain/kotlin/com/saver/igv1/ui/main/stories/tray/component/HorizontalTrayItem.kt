package com.saver.igv1.ui.main.stories.tray.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.saver.igv1.business.domain.models.stories.StoriesTrayInfo

@Composable
fun HorizontalTrayItem(
    storiesTrayInfo: StoriesTrayInfo, onItemClicked: () -> Unit = {}
) {

    val interactionSource = remember {
        MutableInteractionSource()
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.wrapContentHeight().width(90.dp).padding(5.dp)
            .clickable(interactionSource, null) { onItemClicked() }) {

        AsyncImage(model = storiesTrayInfo.user?.profile_pic_url,
            contentDescription = null,
            modifier = Modifier.clip(CircleShape),
            onError = {
                println("HorizontalTrayItem AsyncImage onError: ${it.result.throwable.message}")
            })

        Spacer(modifier = Modifier.height(8.dp))

        storiesTrayInfo.user?.username?.let {
            Text(
                text = it,
                fontSize = 13.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }

}