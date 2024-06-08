package com.ajailani.composeexperiment.ui.screen.experiment

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

val CHOSEN_CONTAINER_HEIGHT = 200.dp
val OPTION_CONTAINER_HEIGHT = 300.dp

@Composable
fun DragAndDropScreen() {
    val chosenItems = remember {
        mutableStateListOf(Item("1", Color.Red), Item("2", Color.Blue), Item("3", Color.Yellow))
    }
    val optionItemGroups = remember {
        mutableStateListOf(
            ItemGroup(
                title = "Title A",
                items = listOf(
                    Item("4", Color.Magenta),
                    Item("5", Color.Cyan),
                    Item("6", Color.Green)
                )
            ),
            ItemGroup(
                title = "Title B",
                items = listOf(Item("4", Color.Magenta))
            ),
            ItemGroup(
                title = "Title C",
                items = listOf(
                    Item("4", Color.Magenta),
                    Item("5", Color.Cyan),
                    Item("6", Color.Green),
                    Item("7", Color.Green)
                )
            )
        )
    }
    val optionItems = remember {
        mutableStateListOf(Item("4", Color.Magenta), Item("5", Color.Cyan), Item("6", Color.Green))
    }

    var draggingItem by remember { mutableStateOf(Item()) }
    var isItemDragging by remember { mutableStateOf(false) }
    var draggableOffset by remember { mutableStateOf(Offset.Zero) }


    Box {
        Column {
            // Chosen items
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(CHOSEN_CONTAINER_HEIGHT),
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(15.dp)
            ) {
                itemsIndexed(chosenItems, key = { _, it -> it.id }) { index, item ->
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .onGloballyPositioned { coordinates ->
                                if (coordinates
                                        .boundsInParent()
                                        .contains(Offset(draggableOffset.x, draggableOffset.y))
                                ) {
                                    if (!isItemDragging) {
                                        chosenItems.remove(draggingItem)
                                        chosenItems.add(index, draggingItem)
                                    }
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        ItemBox(
                            item = item,
                            isDraggable = true,
                            onDragStart = { offset ->
                                draggableOffset = offset
                                draggingItem = item
                                isItemDragging = true

                                Log.d("TAG", "Dragging item: $draggingItem")
                            },
                            onDrag = { offset ->
                                draggableOffset += offset
                            },
                            onDragEnd = {
                                isItemDragging = false
                            }
                        )
                    }
                }
            }

            // Option items
            LazyVerticalGrid(
                modifier = Modifier
                    .background(Color.Gray)
                    .fillMaxWidth()
                    .height(OPTION_CONTAINER_HEIGHT),
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(15.dp)
            ) {
                items(optionItems) { item ->
                    if (draggingItem != item) {
                        ItemBox(
                            item = item,
                            isDraggable = true,
                            onDragStart = { offset ->
                                draggableOffset = offset
                                isItemDragging = true
                                draggingItem = item
                            },
                            onDrag = { offset ->
                                draggableOffset += offset
                            },
                            onDragEnd = {
                                isItemDragging = false
                            }
                        )
                    }
                }
            }
        }

        // ItemBox Snapshot
        if (isItemDragging) {
            ItemBoxSnapshot(
                item = draggingItem,
                offsetX = draggableOffset.x,
                offsetY = draggableOffset.y
            )
        }
    }
}

@Composable
fun ItemBox(
    item: Item,
    isDraggable: Boolean = false,
    onDragStart: ((Offset) -> Unit)? = null,
    onDrag: ((Offset) -> Unit)? = null,
    onDragEnd: (() -> Unit)? = null
) {
    var isDragStarted by remember { mutableStateOf(false) }
    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }

    Column(
        modifier = Modifier
            .onGloballyPositioned {
                offsetX = it.positionInRoot().x
                offsetY = it.positionInRoot().y
            }
            .then(
                if (isDraggable) {
                    Modifier
                        .pointerInput(Unit) {
                            detectDragGestures(
                                onDragStart = {
                                    isDragStarted = true
                                    onDragStart?.invoke(Offset(offsetX, offsetY))
                                },
                                onDrag = { change, offset ->
                                    change.consume()
                                    onDrag?.invoke(offset)
                                },
                                onDragEnd = {
                                    isDragStarted = false
                                    onDragEnd?.invoke()
                                }
                            )
                        }
                } else Modifier
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!isDragStarted) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .size(80.dp)
                    .background(color = item.color)
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = item.id)
        }
    }
}

@Composable
fun ItemBoxSnapshot(
    item: Item,
    offsetX: Float,
    offsetY: Float
) {
    Column(
        modifier = Modifier
            .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(10.dp))
            .background(color = Color.White, shape = RoundedCornerShape(10.dp))
            .padding(7.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .size(80.dp)
                .background(color = item.color)
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = item.id)
    }
}

data class ItemGroup(
    val title: String,
    val items: List<Item>
)

data class Item(
    val id: String = "",
    val color: Color = Color.Unspecified
)