package com.saver.igv1.ui.main.common.bs


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saver.igv1.Colors
import com.saver.igv1.business.domain.ModalBottomSheetInfo
import com.saver.igv1.ui.main.common.utils.ModifierExtensionUtils.clickable
import instagramsaverv1.igsaver.generated.resources.Res
import instagramsaverv1.igsaver.generated.resources.download
import instagramsaverv1.igsaver.generated.resources.ic_baseline_play_arrow_24
import instagramsaverv1.igsaver.generated.resources.ic_baseline_share_24
import instagramsaverv1.igsaver.generated.resources.ic_file_download_24dp
import instagramsaverv1.igsaver.generated.resources.play
import instagramsaverv1.igsaver.generated.resources.share
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DownloadBs(
    modalBottomSheetInfo: ModalBottomSheetInfo.DownloadBs,
    modalBottomSheetState: ModalBottomSheetState
) {

    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        OptionItem(
            title = stringResource(Res.string.download), icon = Res.drawable.ic_file_download_24dp
        ) {
            modalBottomSheetInfo.onDownloadClicked.invoke()
            coroutineScope.launch {
                modalBottomSheetState.hide()
            }
        }

        OptionItem(
            title = stringResource(Res.string.play), icon = Res.drawable.ic_baseline_play_arrow_24
        ) {
            modalBottomSheetInfo.onPlayClicked.invoke()
            coroutineScope.launch {
                modalBottomSheetState.hide()
            }
        }

        OptionItem(
            title = stringResource(Res.string.share), icon = Res.drawable.ic_baseline_share_24
        ) {
            modalBottomSheetInfo.onShareClicked.invoke()
            coroutineScope.launch {
                modalBottomSheetState.hide()
            }
        }
    }
}

@Composable
private fun OptionItem(
    title: String, icon: DrawableResource, onItemClicked: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 20.dp).padding(vertical = 6.dp)
            .height(45.dp).clickable {
                onItemClicked()
            }, verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = painterResource(icon),
            contentDescription = null,
            colorFilter = ColorFilter.tint(Colors.white),
            modifier = Modifier.size(25.dp),
        )

        Spacer(modifier = Modifier.width(5.dp))

        Text(
            text = title, color = Colors.white, fontSize = 16.sp
        )
    }
}