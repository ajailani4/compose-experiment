package com.ajailani.experiment.ui.navigation

sealed class Graph(val route: String) {
    data object Auth : Graph("auth_graph")

    data object Home : Graph("home_graph")
}
