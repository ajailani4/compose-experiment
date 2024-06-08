package com.ajailani.experiment.ui.navigation

sealed class Screen(val route: String) {
    data object Login : Screen("login_screen")

    data object Register : Screen("register_screen")

    data object Home : Screen("home_screen")

    data object Movies : Screen("movies_screen")

    data object Detail : Screen("detail_screen")
}
