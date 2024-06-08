package com.ajailani.composeexperiment.ui.screen.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class DetailViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val fullName: String? = savedStateHandle["fullName"]
}