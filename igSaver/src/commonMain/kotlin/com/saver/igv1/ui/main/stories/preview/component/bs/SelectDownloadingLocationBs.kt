package com.saver.igv1.ui.main.stories.preview.component.bs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxColors
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saver.igv1.Colors
import com.saver.igv1.business.domain.DownloadingLocationOptions
import com.saver.igv1.business.domain.SelectedDownloadingLocationOptionData
import com.saver.igv1.ui.main.common.components.GenericCircularCheckBox
import com.saver.igv1.ui.main.common.utils.ModifierExtensionUtils.clickable
import instagramsaverv1.igsaver.generated.resources.Res
import instagramsaverv1.igsaver.generated.resources.download
import org.jetbrains.compose.resources.stringResource


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SelectDownloadingLocationBs(
    modalBottomSheetState: ModalBottomSheetState,
    onDownloadingOptionSelected: (SelectedDownloadingLocationOptionData) -> Unit
) {

    val selectedDownloadOption: MutableState<DownloadingLocationOptions?> = remember { mutableStateOf(null) }
    val isNotAskAgainSelected: MutableState<Boolean> = remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth().padding(15.dp)) {

        Text(
            "Select Download Option",
            fontSize = 15.sp
        )

        SelectItem(
            selectedDownloadOption,
            DownloadingLocationOptions.InPhone,
            "Download in Phone: ",
            "Save to your device for quick access."
        )


        SelectItem(
            selectedDownloadOption,
            DownloadingLocationOptions.InDrive,
            "Download in Drive: ",
            "Save securely in your Drive and access anytime."
        )



        SelectItem(
            selectedDownloadOption,
            DownloadingLocationOptions.InBoth,
            "Download in Both: ",
            "Save securely in your Drive and device."
        )

        Surface(
            modifier = Modifier.fillMaxWidth().clickable(true) {
                selectedDownloadOption.value?.let {
                    SelectedDownloadingLocationOptionData(
                        downloadingOption = it,
                        notAskAgainSelected = isNotAskAgainSelected.value
                    )
                }?.let {
                    onDownloadingOptionSelected.invoke(
                        it
                    )
                }
            },
            color = MaterialTheme.colors.primary,
            shape = RoundedCornerShape(25.dp)
        ) {
            Text(
                stringResource(Res.string.download),
                modifier = Modifier.padding(10.dp),
                color = MaterialTheme.colors.onPrimary,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        if (selectedDownloadOption.value != null) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Checkbox(
                    checked = isNotAskAgainSelected.value,
                    onCheckedChange = {
                        isNotAskAgainSelected.value = it
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Colors.white,
                    )
                )

                Text(
                    "Set as default location\nYou can modify it anytime in the settings",
                    fontSize = 11.sp,
                    lineHeight = 16.sp
                )
            }
        }
    }
}

@Composable
private fun SelectItem(
    selectedDownloadOption: MutableState<DownloadingLocationOptions?>,
    downloadingOption: DownloadingLocationOptions,
    title: String,
    subtitle: String
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(15.dp)
            .clickable() {
                if (selectedDownloadOption.value == downloadingOption) {
                    selectedDownloadOption.value = null
                } else {
                    selectedDownloadOption.value = downloadingOption
                }
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        GenericCircularCheckBox(
            isChecked = mutableStateOf(selectedDownloadOption.value == downloadingOption)
        ) {
            if (selectedDownloadOption.value == downloadingOption) {
                selectedDownloadOption.value = null
            } else {
                selectedDownloadOption.value = downloadingOption
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        val annotatedStr = buildAnnotatedString {
            withStyle(
                SpanStyle(
                    fontSize = 13.sp
                )
            ) {
                append(title)
            }

            withStyle(
                SpanStyle(
                    fontSize = 11.sp,
                    color = MaterialTheme.colors.primary
                )
            ) {
                append(subtitle)
            }
        }

        Text(
            annotatedStr,
            lineHeight = 16.sp
        )
    }
}