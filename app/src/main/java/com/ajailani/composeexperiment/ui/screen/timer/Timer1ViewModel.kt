package com.ajailani.composeexperiment.ui.screen.timer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajailani.composeexperiment.data.local.PreferencesDataStore
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Timer1ViewModel(
    private val preferencesDataStore: PreferencesDataStore
) : ViewModel() {
    var timer by mutableLongStateOf(0L)
        private set

    init {
        getTimer()
    }

    private fun getTimer() {
        viewModelScope.launch {
            preferencesDataStore.getTimer().collect {
                timer = it
            }
        }
    }

    fun saveTimer() {
        viewModelScope.launch {
            while (true) {
                delay(1000)
                val tempTimer = timer + 1
                preferencesDataStore.saveTimer(tempTimer)
            }
        }
    }
}