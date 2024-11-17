package com.saver.igv1.ui.main.stories.preview.component.bs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.saver.igv1.Colors
import com.saver.igv1.business.domain.models.stories.StoryInteractionsMetaData
import com.saver.igv1.ui.main.common.utils.ModifierExtensionUtils.clickable


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun IgMentionedUserBs(
    storyInteractionsMetaData: StoryInteractionsMetaData,
    title: String = "People in this Story",
    modalBottomSheetState:ModalBottomSheetState,
    onItemClicked: () -> Unit = {}
) {

    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {

        Text(
            text = title,
            fontSize = 15.sp,
            modifier = Modifier.padding(top = 12.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn {
            items(
                storyInteractionsMetaData.storyIgMentionMetaData ?: listOf()
            ) { storyIgMentionMetaData ->

                println("54654654 storyIgMentionMetaData: $storyIgMentionMetaData")

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().height(100.dp).padding(20.dp)
                ) {

                    AsyncImage(
                        model = storyIgMentionMetaData.profile_url,
                        contentDescription = null,
                        modifier = Modifier.clip(CircleShape).size(50.dp)
                    )

                    Spacer(modifier = Modifier.width(15.dp))

                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.Center
                    ) {

                        storyIgMentionMetaData.fullname?.let {
                            Text(
                                text = it,
                                fontSize = 14.sp,
                                color = Colors.white
                            )
                        }

                        storyIgMentionMetaData.username?.let {
                            Text(
                                text = it,
                                fontSize = 12.sp,
                                color = Colors.white
                            )
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = MaterialTheme.colors.primary,
                        elevation = 5.dp,
                        modifier = Modifier.clickable(showClickIndication = false) {
                            onItemClicked.invoke()
                        }
                    ) {
                        Text(
                            text = "Visit",
                            fontSize = 13.sp,
                            modifier = Modifier.padding(vertical = 5.dp, horizontal = 15.dp),
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }
    }
}