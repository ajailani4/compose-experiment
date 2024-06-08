package com.ajailani.experiment.ui.screen.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class HomeViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val fullName: String? = savedStateHandle["fullName"]
}