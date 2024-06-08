package com.ajailani.composeexperiment.ui.screen.experiment

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel

class DragAndDropViewModel : ViewModel() {
    val chosenItems = mutableStateListOf(
        Item("1", Color.Red), Item("2", Color.Blue), Item("3", Color.Yellow)
    )
    val optionItems = mutableStateListOf(
        Item("4", Color.Magenta), Item("5", Color.Cyan), Item("6", Color.Green)
    )

    fun swapItem(index: Int, draggingItem: Item) {
        val temp = chosenItems[index]
        chosenItems[index] = draggingItem
//        chosenItems[chosenItems.indexOf(draggingItem)] = temp
    }
}