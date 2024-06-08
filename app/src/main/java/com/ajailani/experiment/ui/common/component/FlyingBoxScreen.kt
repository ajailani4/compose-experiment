package com.ajailani.experiment.ui.common.component

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun FlyingBoxScreen() {
    val containerItemsPosition = remember { mutableStateListOf<Offset>() }

    val flyingBoxItems = remember {
        mutableStateListOf(
            FlyingBoxItem(id = "1", color = Color.Gray),
            FlyingBoxItem(id = "2", color = Color.Blue),
            FlyingBoxItem(id = "3", color = Color.DarkGray)
        )
    }

    var fillableContainerIndex by remember { mutableIntStateOf(0) }
    var selectedFlyingBoxItem by remember { mutableStateOf<FlyingBoxItem?>(null) }

    Box {
        Column {
            LazyColumn(
                modifier = Modifier.padding(20.dp)
            ) {
                item {
                    VerticalGrid(
                        columns = 3,
                        verticalGap = 10.dp,
                        horizontalGap = 10.dp
                    ) {
                        items(3) {
                            Box(
                                modifier = Modifier
                                    .border(width = 1.dp, color = Color.Black)
                                    .size(100.dp)
                                    .onGloballyPositioned { coordinates ->
                                        containerItemsPosition.add(coordinates.positionInRoot())
                                    }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(50.dp))
                }
            }

            LazyColumn(
                modifier = Modifier.padding(20.dp)
            ) {
                item {
                    VerticalGrid(
                        columns = 3,
                        verticalGap = 10.dp,
                        horizontalGap = 10.dp
                    ) {
                        items(flyingBoxItems.size) { index ->
                            val flyingBoxItem = flyingBoxItems[index]

                            FlyingBox(
                                item = flyingBoxItem,
                                containerItemsPosition = containerItemsPosition,
                                onPositionCalculated = {
                                    flyingBoxItems[index] = flyingBoxItem.copy(position = it)
                                },
                                onSelected = {
                                    flyingBoxItems[index] = flyingBoxItem.copy(targetFillableContainerIndex = fillableContainerIndex)
                                    fillableContainerIndex = fillableContainerIndex.inc()
                                    selectedFlyingBoxItem = flyingBoxItems[index]
                                },
                                onUnselected = {
                                    flyingBoxItems[index] = flyingBoxItem.copy(targetFillableContainerIndex = 0)
                                    fillableContainerIndex = fillableContainerIndex.dec()
                                }
                            )
                        }
                    }
                }
            }
        }

        if (selectedFlyingBoxItem != null) {
            FlyingBoxSnapshot(
                initializedPosition = flyingBoxItems[0].position,
                selectedFlyingBoxItem = selectedFlyingBoxItem,
                containerItemsPosition = containerItemsPosition
            )
        }
    }
}

@Composable
fun FlyingBoxSnapshot(
    initializedPosition: Offset,
    selectedFlyingBoxItem: FlyingBoxItem?,
    containerItemsPosition: List<Offset>
) {
    val animatableBoxTranslation = remember { Animatable(initializedPosition, Offset.VectorConverter) }

    LaunchedEffect(selectedFlyingBoxItem) {
        selectedFlyingBoxItem?.let {
            animatableBoxTranslation.snapTo(Offset(it.position.x, it.position.y))
            animatableBoxTranslation.animateTo(
                targetValue = Offset(
                    containerItemsPosition[it.targetFillableContainerIndex].x,
                    containerItemsPosition[it.targetFillableContainerIndex].y
                )
            )
        }
    }

    Box(
        modifier = Modifier
            .graphicsLayer {
                translationX = animatableBoxTranslation.value.x
                translationY = animatableBoxTranslation.value.y
            }
            .background(Color.Gray)
            .size(100.dp)
    )
}

@Composable
private fun FlyingBox(
    item: FlyingBoxItem,
    containerItemsPosition: List<Offset>,
    onPositionCalculated: (Offset) -> Unit,
//    onTranslationAnimated: (Offset) -> Unit,
    onSelected: () -> Unit,
    onUnselected: () -> Unit
) {
    var isSelected by remember { mutableStateOf(false) }
    var initialPosition by remember { mutableStateOf(Offset.Zero) }
    /*val animatedBoxTranslation by animateOffsetAsState(
        targetValue = if (isSelected) {
            Offset(
                x = containerItemsPosition[item.targetFillableContainerIndex].x - initialPosition.x,
                y = containerItemsPosition[item.targetFillableContainerIndex].y - initialPosition.y
            )
        } else Offset.Zero,
        label = "${item.id} translation animation"
    )*/

    /*LaunchedEffect(animatedBoxTranslation) {
        onTranslationAnimated(animatedBoxTranslation)
    }*/

    Box(
        modifier = Modifier
            /*.graphicsLayer {
                translationX = animatedBoxTranslation.x
                translationY = animatedBoxTranslation.y
            }*/
            .background(item.color)
            .size(100.dp)
            .onGloballyPositioned {
                onPositionCalculated(it.positionInRoot())
                initialPosition = it.positionInRoot()
                Log.d("FlyingBoxScreen", "calculate position ${item.id}: ${it.positionInRoot()}")
            }
            .clickable {
                if (isSelected) onUnselected() else onSelected()
                isSelected = !isSelected
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = item.id,
            color = Color.White,
            style = MaterialTheme.typography.titleLarge
        )
    }
}

data class FlyingBoxItem(
    val id: String,
    val color: Color,
    val isSelected: Boolean = false,
    val position: Offset = Offset.Zero,
    val targetFillableContainerIndex: Int = 0
)

@Preview(showBackground = true)
@Composable
private fun PreviewFlyingBoxScreen() {
    FlyingBoxScreen()
}