package com.ajailani.composeexperiment.ui.screen.experiment

import android.util.Log
import androidx.compose.animation.core.exponentialDecay
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import com.ajailani.composeexperiment.ui.screen.experiment.BottomSheetState.EXPANDED
import com.ajailani.composeexperiment.ui.screen.experiment.BottomSheetState.HALF_OPENED
import kotlin.math.roundToInt

@Composable
fun NestedScrollScreen() {
    val items = remember { (1..30).map { it } }

    val customBottomSheetState = rememberCustomBottomSheetState()
    val lazyListState = rememberLazyListState()

    Box(modifier = Modifier.background(Color.White)) {
        Button(
            modifier = Modifier.align(Alignment.TopCenter),
            onClick = { customBottomSheetState.value = HALF_OPENED }
        ) {
            Text(text = "Show Custom Bottom Sheet")
        }
        CustomBottomSheet(
            state = customBottomSheetState,
            scrollState = lazyListState
        ) {
            LazyColumn(
                contentPadding = PaddingValues(10.dp),
                state = lazyListState
            ) {
                items(items) {
                    Item(it)
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CustomBottomSheet(
    state: MutableState<BottomSheetState>,
    shape: Shape = RoundedCornerShape(
        topStart = 20.dp,
        topEnd = 20.dp,
        bottomStart = 0.dp,
        bottomEnd = 0.dp
    ),
    backgroundColor: Color = Color.White,
    scrollState: LazyListState,
    content: @Composable () -> Unit
) {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val screenHeight = configuration.screenHeightDp.dp
    val halfOpenedContentHeight = remember { screenHeight * 0.3f }
    val anchors = remember {
        with(density) {
            DraggableAnchors {
                EXPANDED at 0f
                HALF_OPENED at halfOpenedContentHeight.toPx()
                BottomSheetState.COLLAPSED at screenHeight.toPx()
            }
        }
    }
    val anchoredDraggableState = remember {
        AnchoredDraggableState(
            initialValue = state.value,
            positionalThreshold = {
                Log.d("AnchoredDraggableState", "Threshold: ${it * 0.5}")
                it * 0.5f
            },
            velocityThreshold = { with(density) { 100.dp.toPx() } },
            snapAnimationSpec = tween(),
            decayAnimationSpec = exponentialDecay()
        ).apply {
            with(density) {
                updateAnchors(
                    DraggableAnchors {
                        EXPANDED at 0f
                        HALF_OPENED at halfOpenedContentHeight.toPx()
                        BottomSheetState.COLLAPSED at screenHeight.toPx()
                    }
                )
            }
        }
    }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                Log.d("Post scroll", "Delta: ${available.y}")

                return Offset(
                    x = 0f,
                    y = anchoredDraggableState.dispatchRawDelta(available.y)
                )
            }

            override suspend fun onPostFling(
                consumed: Velocity,
                available: Velocity
            ): Velocity {
                Log.d("Post fling", "Velocity: ${available.y}")
                anchoredDraggableState.settle(available.y)
                return super.onPostFling(consumed, available)
            }
        }
    }

    LaunchedEffect(anchoredDraggableState.offset) {
        Log.d("AnchoredDraggableState","Offset: ${anchoredDraggableState.requireOffset()}")
    }

    LaunchedEffect(state.value) {
        anchoredDraggableState.animateTo(state.value)
    }

    LaunchedEffect(anchoredDraggableState.currentValue) {
        state.value = anchoredDraggableState.currentValue
    }

    Box(
        modifier = Modifier
            .offset {
                IntOffset(
                    x = 0,
                    y = anchoredDraggableState
                        .requireOffset()
                        .roundToInt()
                )
            }
            .anchoredDraggable(
                state = anchoredDraggableState,
                orientation = Orientation.Vertical
            )
//            .nestedScroll(nestedScrollConnection)
            .then(
                if (!scrollState.canScrollBackward || !scrollState.canScrollForward) {
                    Modifier.nestedScroll(nestedScrollConnection)
                } else Modifier
            )
            .shadow(elevation = 15.dp, shape = shape)
            .clip(shape)
            .background(color = backgroundColor)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            HorizontalDivider(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .width(80.dp)
                    .clip(CircleShape)
                    .align(Alignment.CenterHorizontally),
                thickness = 5.dp
            )
            Spacer(modifier = Modifier.height(10.dp))
            content()
        }
    }
}

@Composable
private fun Item(index: Int) {
    Card(
        modifier = Modifier
            .padding(bottom = 10.dp)
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.padding(10.dp),
            text = "Item $index"
        )
    }
}

@Composable
fun rememberCustomBottomSheetState() = rememberSaveable {
    mutableStateOf(BottomSheetState.COLLAPSED)
}

enum class BottomSheetState {
    EXPANDED,
    HALF_OPENED,
    COLLAPSED
}