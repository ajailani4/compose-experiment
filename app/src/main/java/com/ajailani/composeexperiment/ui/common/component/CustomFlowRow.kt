package com.ajailani.composeexperiment.ui.common.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize

/** Unfinished */
@Composable
fun CustomFlowRow() {
    val boxItems = remember {
        mutableStateListOf(
            BoxItem(
                width = 132.dp,
                height = 124.dp,
                color = Color(0xFF71DBD3)
            ),
            BoxItem(
                width = 44.dp,
                height = 56.dp,
                color = Color(0xFFECF789)
            ),
            BoxItem(
                width = 54.dp,
                height = 56.dp,
                color = Color(0xFFFDA9FF)
            ),
            BoxItem(
                width = 97.dp,
                height = 56.dp,
                color = Color(0xFFFFC533)
            ),
            BoxItem(
                width = 132.dp,
                height = 68.dp,
                color = Color(0xFFFF8736)
            ),
            BoxItem(
                width = 63.dp,
                height = 68.dp,
                color = Color(0xFF9B7EDC)
            ),
        )
    }

    var parentSize by remember { mutableStateOf(Size.Zero) }
    val maxHeight = remember { 124.dp }

    Layout(
        modifier = Modifier
            .wrapContentHeight()
            .onGloballyPositioned {
                parentSize = it.size.toSize()
            },
        content = {
            boxItems.forEach {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(32.dp))
                        .size(width = it.width, height = it.height)
                        .background(it.color)
                )
            }
        }
    ) { measurables, constraints ->
        val placeables = measurables.map { it.measure(constraints) }

        layout(constraints.maxWidth, 500) {
            var xPosition = 0
            var yPosition = 0
            var xPositionAfterMaxItem = 0
            var previousPlaceable: Placeable? = null

            placeables.forEach { placeable ->
                val remainingRowSpace = constraints.maxWidth - xPosition

                if (placeable.width > remainingRowSpace) {
                    yPosition += previousPlaceable?.height ?: 0
                    xPosition = xPositionAfterMaxItem
                }

                if (placeable.height >= maxHeight.roundToPx()) {
                    xPositionAfterMaxItem = placeable.width
                }

                placeable.place(x = xPosition, y = yPosition)
                xPosition += placeable.width

                previousPlaceable = placeable
            }
        }
    }
}

data class BoxItem(
    val width: Dp,
    val height: Dp,
    val color: Color
)