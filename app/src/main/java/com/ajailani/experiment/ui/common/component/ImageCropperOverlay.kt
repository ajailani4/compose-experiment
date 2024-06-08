package com.ajailani.experiment.ui.common.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer

@Composable
fun ImageCropperOverlay(
    processCroppedImage: (Size) -> Unit
) {
    val rectColor = Color.Black
    var circleRadius by remember { mutableFloatStateOf(0f) }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer { alpha = 0.99f }
    ) {
        circleRadius = size.minDimension / 2.0f

        drawRect(
            alpha = 0.5f,
            color = rectColor
        )
        drawCircle(
            blendMode = BlendMode.Clear,
            color = Color.Transparent,
            radius = circleRadius
        )

        processCroppedImage(Size(circleRadius, circleRadius))
    }
}