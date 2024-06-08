package com.ajailani.composeexperiment.ui.screen.experiment

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput

@Composable
fun DrawingScreen() {
    val lines = remember { mutableStateListOf<Line>() }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()

                    val line = Line(
                        start = change.position - dragAmount,
                        end = change.position
                    )

                    Log.d("TAG", "$line")

                    lines.add(line)
                }
            }
    ) {
        lines.forEach {
            drawLine(
                color = Color.Black,
                start = it.start,
                end = it.end,
                strokeWidth = 1f
            )
        }
    }
}

data class Line(
    val start: Offset,
    val end: Offset
)