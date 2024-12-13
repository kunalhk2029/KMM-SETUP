package com.cc.referral.ui.main.cards.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.cc.referral.AppColors
import creditcardreferral.creditcardreferral.generated.resources.Res
import creditcardreferral.creditcardreferral.generated.resources.ic_demo_card
import creditcardreferral.creditcardreferral.generated.resources.ic_demo_card2
import org.jetbrains.compose.resources.painterResource


@Composable
fun CardBrandingComponent(
    modifier: Modifier
) {

//    Box(contentAlignment = Alignment.Center, modifier = modifier.size(100.dp)) {
//        Canvas(
//            modifier = Modifier
//                .size(15.dp).align(Alignment.TopStart) // Set a size for the canvas
//        ) {
//            val canvasWidth = size.width
//            val canvasHeight = size.height
//            // Draw a small circle at the center of the canvas
//            drawCircle(
//                color = AppColors.blue70C3E8, // Circle color
//                radius = 15f,       // Radius of the circle
//                center = Offset(-30f, 70f) // Position at the center
//            )
//        }
//
//        Canvas(modifier = Modifier.size(20.dp).align(Alignment.BottomStart)) {
//            val radius = 20.dp.toPx() / 2
//            val stroke = 6.dp.toPx()
//            val center = Offset(0f, 0f) // Circle's center near the top-left of the canvas
//
//            drawCircle(
//                color = AppColors.lightGreen,
//                radius = radius,
//                style = Stroke(width = stroke),
//                center = center
//            )
//        }
//
//        Canvas(modifier = Modifier.size(20.dp).align(Alignment.TopEnd)) {
//            val radius = 10.dp.toPx() / 2
//            val stroke = 3.dp.toPx()
//            val center = Offset(65f, 75f) // Circle's center near the top-left of the canvas
//
//            drawCircle(
//                color = AppColors.redE87070,
//                radius = radius,
//                style = Stroke(width = stroke),
//                center = center
//            )
//        }
//
//        Image(
//            painter = painterResource(Res.drawable.ic_demo_card),
//            modifier = Modifier
//                .size(95.dp)
////                .padding(horizontal = 5.dp)
////                .align(Alignment.Center),
//            ,contentDescription = null
//        )
//    }


    Image(
        painter = painterResource(
            Res.drawable.ic_demo_card2
        ),
        contentDescription = null,
        modifier = modifier
    )
}