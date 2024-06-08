package com.ajailani.experiment.ui.common.component

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SwapCubeScreen() {
    val items = listOf(Color.Gray, Color.Red, Color.DarkGray, Color.Blue, Color.Magenta)
    val pagerState = rememberPagerState { items.size }

    HorizontalPager(state = pagerState) {
        Box(
            modifier = Modifier
                .cubicTransition(page = it, pagerState = pagerState)
                .fillMaxSize()
                .background(items[it])
        )
    }

    /*Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        *//*Box(
            modifier = Modifier
                .graphicsLayer {
                    rotationY = -0f
                    transformOrigin = TransformOrigin(1f, 0.5f)
                }
                .fillMaxWidth()
                .height(300.dp)
                .background(Color.Gray)
        )
        Box(
            modifier = Modifier
                .background(Color.Gray)
                .padding(20.dp)
                .size(100.dp)
        ) {
            Text(text = "Test")
        }*//*
    }*/
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Modifier.cubicTransition(page: Int, pagerState: PagerState) =
    graphicsLayer {
        val pageOffset = (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
        val directionFraction = if (pageOffset < 0f) 1 else -1

        rotationY = (directionFraction * (pageOffset.absoluteValue * 30f)).coerceIn(-90f, 90f)
        transformOrigin = TransformOrigin(
            pivotFractionX = if (directionFraction == 1) 0f else 1f,
            pivotFractionY = 0.5f
        )

        Log.d("SwapCubeScreen", "page $page has $pageOffset page offset and $directionFraction direction fraction")
        Log.d("SwapCubeScreen", "cur offset fraction ${pagerState.currentPageOffsetFraction}")
        Log.d("SwapCubeScreen", "page $page has $rotationY rotationY")
    }

@Composable
fun SwapCubeScreen(color: Color) {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val screenHeightPx = with(density) { configuration.screenHeightDp.dp.toPx() }
    val screenWidthPx = with(density) { configuration.screenWidthDp.dp.toPx() }

    var initialSwipeDirection by remember { mutableStateOf<SwipeDirection?>(null) }
    var topLeftX by remember { mutableFloatStateOf(0f) }
    var topLeftY by remember { mutableFloatStateOf(0f) }
    var topRightX by remember { mutableFloatStateOf(screenWidthPx) }
    var topRightY by remember { mutableFloatStateOf(0f) }
    var bottomLeftX by remember { mutableFloatStateOf(0f) }
    var bottomLeftY by remember { mutableFloatStateOf(screenHeightPx) }
    var bottomRightX by remember { mutableFloatStateOf(screenWidthPx) }
    var bottomRightY by remember { mutableFloatStateOf(screenHeightPx) }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragStart = {
                        Log.d("CubicRectangle", "initial offset: $it")
                    },
                    onHorizontalDrag = { change, dragAmount ->
                        Log.d("CubicRectangle", "topRightX: $topRightX")
                        Log.d("CubicRectangle", "offset: $dragAmount")
                        Log.d("CubicRectangle", "previous position: ${change.position}")

                        if (initialSwipeDirection == null) {
                            initialSwipeDirection = if (dragAmount < 0) SwipeDirection.LEFT else SwipeDirection.RIGHT
                        }

                        if (initialSwipeDirection == SwipeDirection.LEFT) {
                            topLeftY += -dragAmount
                            topRightX += dragAmount
                            bottomLeftY -= -dragAmount
                            bottomRightX += dragAmount
                        } else {
                            topRightY += dragAmount
                            topLeftX += dragAmount
                            bottomRightY -= dragAmount
                            bottomLeftX += dragAmount
                        }

                        change.consume()
                    },
                    onDragEnd = {
                        topLeftY = 0f
                        topRightX = screenWidthPx
                        bottomLeftY = screenHeightPx
                        bottomRightX = screenWidthPx
                        topRightY = 0f
                        topLeftX = 0f
                        bottomRightY = screenHeightPx
                        bottomLeftX = 0f

                        initialSwipeDirection = null
                    }
                )
            }
    ) {
        val path = Path().apply {
            moveTo(topLeftX, topLeftY)
            lineTo(topRightX, topRightY)
            lineTo(bottomRightX, bottomRightY)
            lineTo(bottomLeftX, bottomLeftY)
        }

        drawPath(
            path = path,
            color = color
        )
    }
}

enum class SwipeDirection {
    LEFT, RIGHT
}

@Preview(showBackground = true)
@Composable
private fun CubicRectanglePreview() {
    SwapCubeScreen()
}