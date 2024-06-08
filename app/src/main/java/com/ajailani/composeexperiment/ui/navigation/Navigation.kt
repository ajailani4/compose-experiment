package com.ajailani.composeexperiment.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.ajailani.composeexperiment.ui.navigation.graph.authGraph
import com.ajailani.composeexperiment.ui.navigation.graph.homeGraph

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Graph.Auth.route) {
        authGraph(navController)
        homeGraph(navController)
    }
}