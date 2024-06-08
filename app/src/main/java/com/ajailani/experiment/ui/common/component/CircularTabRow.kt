package com.ajailani.experiment.ui.common.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.ajailani.experiment.ui.theme.NoRippleTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CircularTabRow(
    content: @Composable (selectedTabIndex: Int) -> Unit
) {
    val titles = listOf("Tab 1", "Tab 2")
    val pagerState = rememberPagerState { titles.size }
    val coroutineScope = rememberCoroutineScope()
    val tabHeight = 47.dp

    CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
        Box(modifier = Modifier.background(color = Color.Gray)) {
            TabRow(
                modifier = Modifier
                    .padding(10.dp)
                    .clip(CircleShape),
                selectedTabIndex = pagerState.currentPage,
                indicator = {
                    if (pagerState.currentPage < it.size) {
                        Box(
                            modifier = Modifier
                                .tabIndicatorOffset(it[pagerState.currentPage])
                                .fillMaxHeight()
                                .padding(4.dp)
                                .clip(CircleShape)
                                .background(color = MaterialTheme.colorScheme.primary)
                                .zIndex(-1f)
                        )
                    }
                },
                divider = {}
            ) {
                titles.forEachIndexed { index, title ->
                    Tab(
                        modifier = Modifier.height(tabHeight),
                        selected = index == pagerState.currentPage,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.scrollToPage(index)
                            }
                        }
                    ) {
                        Text(
                            text = title,
                            color = if (index == pagerState.currentPage)
                                MaterialTheme.colorScheme.onPrimary
                            else
                                MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }

    HorizontalPager(state = pagerState) { page ->
        content(page)
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFA3A3A3)
@Composable
fun PreviewCustomTab() {
    CircularTabRow(content = {})
}