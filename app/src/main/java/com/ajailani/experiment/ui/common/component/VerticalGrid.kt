package com.ajailani.experiment.ui.common.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.ceil

@Composable
fun VerticalGrid(
    columns: Int,
    verticalGap: Dp = 0.dp,
    horizontalGap: Dp = 0.dp,
    content: VerticalGridScope.() -> Unit
) {
    val verticalGridScope = remember { VerticalGridScope() }

    Layout(
        content = {
            verticalGridScope.content()

            repeat(verticalGridScope.itemSize) {
                verticalGridScope.itemContent(it)
            }
        }
    ) { measurables, constraints ->
        val verticalGapPx = verticalGap.roundToPx()
        val horizontalGapPx = horizontalGap.roundToPx()
        val totalRow = ceil(verticalGridScope.itemSize.toDouble() / columns.toDouble()).toInt()
        val totalHorizontalGapSize = horizontalGapPx * (columns - 1)
        val totalVerticalGapSize = verticalGapPx * (totalRow - 1)

        val placeables = measurables.map {
            it.measure(
                Constraints.fixedWidth((constraints.maxWidth - totalHorizontalGapSize) / columns)
            )
        }

        val maxRowHeights = measureMaxRowHeights(
            placeables = placeables,
            columns = columns
        )

        layout(
            width = constraints.maxWidth,
            height = measureLayoutHeight(
                rowMaxHeights = maxRowHeights,
                totalVerticalGapSize = totalVerticalGapSize
            )
        ) {
            var xPosition = 0
            var yPosition = 0
            var row = 0

            placeables.forEachIndexed { index, placeable ->
                placeable.placeRelative(xPosition, yPosition)

                if ((index + 1) % columns == 0) {
                    xPosition = 0
                    yPosition += maxRowHeights[row] + verticalGapPx
                    row = row.inc()
                } else xPosition += placeable.width + horizontalGapPx
            }
        }
    }
}

private fun measureMaxRowHeights(
    placeables: List<Placeable>,
    columns: Int
): MutableList<Int> {
    val result = mutableListOf<Int>()
    var maxHeightInRow = 0

    placeables.forEachIndexed { index, placeable ->
        if (placeable.height > maxHeightInRow) {
            maxHeightInRow = placeable.height
        }

        if ((index + 1) % columns == 0 || index == placeables.lastIndex) {
            result.add(maxHeightInRow)
            maxHeightInRow = 0
        }
    }

    return result
}

private fun measureLayoutHeight(
    rowMaxHeights: List<Int>,
    totalVerticalGapSize: Int
) = if (totalVerticalGapSize >= 0) rowMaxHeights.sum() + totalVerticalGapSize else 0

class VerticalGridScope {
    var itemSize = 0
    lateinit var itemContent: @Composable (index: Int) -> Unit

    fun items(count: Int, content: @Composable (index: Int) -> Unit) {
        itemSize = count
        itemContent = content
    }

    fun <T> items(items: List<T>, content: @Composable (T) -> Unit) {
        items(
            count = items.size,
            content = { content(items[it]) }
        )
    }
}

@Composable
fun XItem(
    id: String,
    name: String,
    description: String
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card {
            Column(modifier = Modifier.padding(10.dp)) {
                Text(text = "ID: $id")
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Name: $name")
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = description)
    }
}

data class XItemData(
    val id: String,
    val name: String,
    val description: String
)

@Preview(showBackground = true)
@Composable
private fun VerticalGridPreview() {
    val xItems = listOf<XItemData>(
        XItemData("1", "Satu", "test\ntest\ntest"),
        XItemData("2", "Dua", "test test test"),
        XItemData("3", "Tiga", "test test test"),
        XItemData("1", "Satu", "test\ntest\ntest"),
        XItemData("2", "Dua", "test test test"),
//        XItemData("3", "Tiga", "test test test"),
//        XItemData("4", "Empat", "test\ntest test"),
//        XItemData("5", "Lima", "test test test"),
//        XItemData("6", "Enam", "test test test")
    )

    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "With item list")
        Spacer(modifier = Modifier.height(5.dp))
        VerticalGrid(
            columns = 3,
            verticalGap = 10.dp,
            horizontalGap = 5.dp
        ) {
            items(items = xItems) {
                XItem(it.id, it.name, it.description)
            }
        }
        /*VerticalGrid(
            columns = 3,
            verticalGap = 10.dp,
            horizontalGap = 5.dp
        ) {
            items(items = xItems) {
                XItem(it.id, it.name, it.description)
            }
        }*/
        /*Text(text = "With item size")
        Spacer(modifier = Modifier.height(5.dp))
        VerticalGrid(
            columns = 3,
            verticalGap = 5.dp,
            horizontalGap = 15.dp
        ) {
            items(6) {
                XItem(id = "$it", name = "$it", description = "$it")
            }
        }*/
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(items = xItems) {
                XItem(it.id, it.name, it.description)
            }
        }
    }
}