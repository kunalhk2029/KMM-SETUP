package com.saver.igv1.ui.main.common.player

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import com.saver.igv1.business.domain.MultipleMediaPlayerManager
import com.saver.igv1.business.domain.SingleMediaPlayerManager
import com.saver.igv1.business.domain.VideoPlayerManager
import kotlinx.cinterop.CValue
import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreGraphics.CGRect
import platform.QuartzCore.CATransaction
import platform.QuartzCore.kCATransactionDisableActions
import platform.UIKit.UIView

@OptIn(ExperimentalForeignApi::class, ExperimentalFoundationApi::class)
@Composable
actual fun PlayerView(
    videoPlayerManager: VideoPlayerManager,
    multipleMediaPlayerManager: MultipleMediaPlayerManager?,
    singleMediaPlayerManager: SingleMediaPlayerManager?,
    modifier: Modifier,
    i: Int,
    pagerState: PagerState?,
    multiMediaPlayer: MutableState<Pair<Int, Any?>?>?,
    singleMediaPlayer: MutableState<Any?>?,
) {

    UIKitView(
        factory = {
            val playerContainer = UIView()
            playerContainer.addSubview(
                videoPlayerManager.avPlayerViewController.view
            )
            playerContainer
        },
        onResize = { view: UIView, rect: CValue<CGRect> ->
            CATransaction.begin()
            CATransaction.setValue(true, kCATransactionDisableActions)
            view.layer.setFrame(rect)
            videoPlayerManager.playbackLayer?.setFrame(rect)
            videoPlayerManager.avPlayerViewController.view.layer.frame = rect
            CATransaction.commit()
        },
        update = {

        }, onRelease = {
            println("7657657 onRelease UIKIT")
        },
        modifier = modifier
    )
}