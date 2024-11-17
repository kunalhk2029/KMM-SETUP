package com.saver.igv1.ui.main.common.player

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import com.saver.igv1.business.domain.MultipleMediaPlayerManager
import com.saver.igv1.business.domain.SingleMediaPlayerManager
import com.saver.igv1.business.domain.VideoPlayerManager

@OptIn(ExperimentalFoundationApi::class)
@Composable
expect fun PlayerView(
    videoPlayerManager: VideoPlayerManager,
    multipleMediaPlayerManager: MultipleMediaPlayerManager?,
    singleMediaPlayerManager: SingleMediaPlayerManager?,
    modifier: Modifier,
    i: Int,
    pagerState: PagerState?,
    multiMediaPlayer: MutableState<Pair<Int, Any?>?>?,
    singleMediaPlayer: MutableState<Any?>?,
)