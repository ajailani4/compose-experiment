package com.ajailani.experiment.ui.common

sealed class UIState<out T> {
    data object Idle : UIState<Nothing>()
    data object Loading : UIState<Nothing>()
    data class Success<T>(val data: T?) : UIState<T>()
    data class Error(val message: String?) : UIState<Nothing>()
}
