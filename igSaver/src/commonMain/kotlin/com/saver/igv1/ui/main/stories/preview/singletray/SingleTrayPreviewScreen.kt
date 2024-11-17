package com.saver.igv1.ui.main.stories.preview.singletray

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.saver.igv1.Colors
import com.saver.igv1.business.domain.ModalBottomSheetInfo
import com.saver.igv1.business.domain.models.stories.getStoryDownloadingOptions
import com.saver.igv1.ui.main.common.components.DefaultScreenUI
import instagramsaverv1.igsaver.generated.resources.Res
import instagramsaverv1.igsaver.generated.resources.ic_resume_icon
import org.jetbrains.compose.resources.painterResource

@Composable
fun SingleTrayPreviewScreen(
    singleTrayPreviewState: SingleTrayPreviewState,
    onEvents: (SingleTrayPreviewEvents) -> Unit,
    onNavEvents: (SingleTrayPreviewNavEvents) -> Unit
) {

    DefaultScreenUI(
        uiComponent = singleTrayPreviewState.uiComponent.collectAsState().value,
        progressBarState = singleTrayPreviewState.progressBarState.collectAsState().value,
        modalBottomSheetInfo = singleTrayPreviewState.activeModalBottomSheetInfo.collectAsState().value,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        onModalBottomSheetClosed = {
            onEvents(
                SingleTrayPreviewEvents.UpdateActiveModalBottomSheetInfo(
                    null
                )
            )
        }
    ) {

        Column(modifier = Modifier.fillMaxSize().padding(vertical = 20.dp)) {

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
            ) {
                itemsIndexed(singleTrayPreviewState.storyMediaData ?: listOf()) { index, it ->
                    Box {

                        AsyncImage(model = it.imageMediaData?.firstOrNull()?.url,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.height(120.dp).clickable {
                                onEvents(
                                    SingleTrayPreviewEvents.UpdateActiveModalBottomSheetInfo(
                                        ModalBottomSheetInfo.DownloadBs(
                                            onDownloadClicked = {
                                                val list = it.getStoryDownloadingOptions()
                                                onEvents(
                                                    SingleTrayPreviewEvents.UpdateActiveModalBottomSheetInfo(
                                                        ModalBottomSheetInfo.SelectDownloadOptionsBs(
                                                            list,
                                                            onItemClicked = { mediaDownloadOptions ->
                                                                onEvents(
                                                                    SingleTrayPreviewEvents.UpdateActiveModalBottomSheetInfo(
                                                                        ModalBottomSheetInfo.SelectDownloadLocationBs(
                                                                            mediaDownloadOptions = mediaDownloadOptions,
                                                                            storyMediaInfo = it
                                                                        )
                                                                    )
                                                                )
                                                            }
                                                        ), list
                                                    )
                                                )
                                            },
                                            onPlayClicked = {
                                                onNavEvents.invoke(
                                                    SingleTrayPreviewNavEvents.NavigateToSingleMediaPlayer(
                                                        index,
                                                        it.user?.username ?: ""
                                                    )
                                                )
                                            },
                                            onShareClicked = {},
                                        )
                                    )
                                )
                            },
                            onError = {
                                println("HorizontalTrayItem AsyncImage onError: ${it.result.throwable.message}")
                            })

                        if (it.isVideo == true) {
                            Row(
                                modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                it.videoDuration?.let { seconds ->
                                    it.formatSecondsToMMSS(seconds).let { formattedDuration ->
                                        Text(
                                            text = formattedDuration,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Colors.white,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier.alignByBaseline()
                                        )
                                    }
                                }

                                Image(
                                    painter = painterResource(Res.drawable.ic_resume_icon),
                                    null,
                                    modifier = Modifier.size(20.dp).padding(horizontal = 2.dp)
                                        .alignByBaseline(),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}