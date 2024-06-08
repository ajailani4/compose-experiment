package com.ajailani.experiment.ui.screen

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ajailani.experiment.data.model.Item
import com.ajailani.experiment.util.verticalScrollbar

@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@Composable
fun ItemListScreen() {
    val items = remember { mutableStateListOf<Item>() }
    var latestId by remember { mutableIntStateOf(items.size) }
    val lazyListState = rememberLazyListState()
    var isItemDeleted by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    latestId += 1

                    items.add(
                        Item(
                            id = "$latestId",
                            content = "Item $latestId",
                            isVisible = false
                        )
                    )
                }
            ) {
                Text(
                    modifier = Modifier.padding(5.dp),
                    text = "Add Item"
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScrollbar(lazyListState),
            state = lazyListState,
            contentPadding = PaddingValues(10.dp)
        ) {
            itemsIndexed(items = items) { index, item ->
                LaunchedEffect(Unit) {
                    items[index] = item.copy(isVisible = true)
                }

                AnimatedVisibility(
                    modifier = Modifier.animateItemPlacement(),
                    visible = item.isVisible,
                    enter = scaleIn(),
                    exit = scaleOut()
                ) {
                    ItemCard(
                        modifier = Modifier.padding(bottom = 10.dp),
                        item = item,
                        onItemDeleted = {
                            items[index] = item.copy(isVisible = false)
                            isItemDeleted = true
                        }
                    )

                    /*if (isItemDeleted) {
                        if (this.transition.currentState == this.transition.targetState) {
                            items.removeAt(index)
                            isItemDeleted = false
                        }
                    }*/

                    Log.d("AnimatedVisibility current state", this.transition.currentState.toString())
                }
            }
        }
    }

    Log.d("Items size", items.size.toString())
}

@Composable
private fun ItemCard(
    modifier: Modifier = Modifier,
    item: Item,
    onItemDeleted: () -> Unit
) {
    Card(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = item.content)
            IconButton(onClick = onItemDeleted) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    tint = MaterialTheme.colorScheme.error,
                    contentDescription = "Delete icon"
                )
            }
        }
    }
}