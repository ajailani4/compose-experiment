package com.ajailani.experiment.util

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.packFloats

@Immutable
@JvmInline
value class ScrollbarTrack(
    val packedValue: Long
) {
    constructor(
        max: Float,
        min: Float
    ) : this(packFloats(max, min))
}

fun Modifier.verticalScrollbar(
    state: LazyListState,
    width: Dp = 4.dp
): Modifier =
    composed {
        val scrollbarColor = MaterialTheme.colorScheme.secondary

        drawWithContent {
            drawContent()

            val firstVisibleElementIndex = state.layoutInfo.visibleItemsInfo.firstOrNull()?.index

            if (firstVisibleElementIndex != null) {
                val elementHeight = this.size.height / state.layoutInfo.totalItemsCount
                val scrollbarHeight = state.layoutInfo.visibleItemsInfo.size * elementHeight
                val scrollbarOffsetY = firstVisibleElementIndex * elementHeight

                drawRect(
                    color = scrollbarColor,
                    topLeft = Offset(
                        x = this.size.width - width.toPx(),
                        y = scrollbarOffsetY
                    ),
                    size = Size(width = width.toPx(), height = scrollbarHeight)
                )
            }
        }
    }