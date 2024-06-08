package com.ajailani.composeexperiment.ui.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ajailani.composeexperiment.ui.navigation.Graph
import com.ajailani.composeexperiment.ui.navigation.Screen
import com.ajailani.composeexperiment.ui.screen.login.LoginScreen
import com.ajailani.composeexperiment.ui.screen.register.RegisterScreen

fun NavGraphBuilder.authGraph(navController: NavController) {
    navigation(startDestination = Screen.Register.route, route = Graph.Auth.route) {
        composable(Screen.Register.route) {
            RegisterScreen(
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Register.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(Screen.Login.route) {
            LoginScreen(
                onNavigateToHome = { fullName ->
                    navController.navigate(Screen.Home.route + "/$fullName") {
                        popUpTo(Screen.Register.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}