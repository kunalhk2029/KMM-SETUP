package com.saver.igv1.ui.main.stories.preview.component.bs

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.saver.igv1.Colors
import com.saver.igv1.business.domain.models.common.MediaDownloadOptions
import instagramsaverv1.igsaver.generated.resources.Res
import instagramsaverv1.igsaver.generated.resources.ic_logo
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SelectDownloadingOptionsBs(
    list: List<MediaDownloadOptions>,
    modalBottomSheetState: ModalBottomSheetState,
    onItemClicked: (MediaDownloadOptions) -> Unit
) {


    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxWidth()) {

        LazyColumn(

        ) {

            itemsIndexed(if (list.size > 3) list.subList(0, 3) else list) { index, item ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 15.dp, vertical = 15.dp)
                        .wrapContentHeight().clickable {
                            coroutineScope.launch {
                                modalBottomSheetState.hide()
                            }
                            onItemClicked.invoke(item)
                        }
                ) {

                    Surface(
                        shape = RoundedCornerShape(15.dp),
                        border = BorderStroke(1.dp, MaterialTheme.colors.primary),
                        modifier = Modifier.size(
                            45.dp
                        )
                            .alpha(0.6f),
                        color = Colors.black,
                    ) {
                        Box(contentAlignment = Alignment.Center) {

                            AsyncImage(
                                model = item.url,
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxSize().alpha(0.6f)

                            )

                            println("67677 Size: ${item.size}")

                            Text(
                                text = if (index == 0) "Original"
                                else if (index == 1) "Full HD"
                                else "HD",
                                fontSize = 8.5.sp,
                                textAlign = TextAlign.Center,
                                color = Colors.white,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(15.dp))

                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.Center
                    ) {

                        val annotatedStr = buildAnnotatedString {
                            withStyle(
                                SpanStyle(
                                    fontSize = 12.sp,
                                )
                            ) {
                                append(
                                    if (index == 0) "Original Quality"
                                    else if (index == 1) "Full HD Quality"
                                    else "HD Quality"
                                )
                            }

                            item.size?.let {

                                withStyle(
                                    SpanStyle(
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colors.primary,
                                    )
                                ) {
                                    append(
                                        "\n" + it
                                    )
                                }
                            }
                        }

                        Text(
                            text = annotatedStr,
                            lineHeight = 18.sp,
                        )
                    }

                    Spacer(modifier = Modifier.width(5.dp))

                    Surface(
                        shape = CircleShape,
                        color = Colors.white,
                        modifier = Modifier.size(27.dp)
                    ) {
                        Image(
                            painter = painterResource(
                                Res.drawable.ic_logo
                            ),
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.size(30.dp).padding(4.dp),
                            colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
                        )
                    }
                }
            }
        }
    }
}