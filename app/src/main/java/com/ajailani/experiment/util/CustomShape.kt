package com.ajailani.experiment.util

import android.util.Log
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

class CustomShape(private val croppedSize: Size) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            Log.d("Cropped size", croppedSize.toString())
            val offsetX = croppedSize.width / 2
            val offsetY = croppedSize.height/ 2

            addRect(
                Rect(
                    offset = Offset(offsetX, offsetY),
                    size = croppedSize
                )
            )
        }

        return Outline.Generic(path = path)
    }
}