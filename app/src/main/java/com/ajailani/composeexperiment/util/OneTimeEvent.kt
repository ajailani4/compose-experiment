package com.ajailani.composeexperiment.util

sealed class OneTimeEvent {
    data class NavigateEvent(val route: String) : OneTimeEvent()
    data class ShowToastEvent(val message: String?) : OneTimeEvent()
}
