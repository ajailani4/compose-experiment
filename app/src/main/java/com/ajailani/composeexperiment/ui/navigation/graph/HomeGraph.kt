package com.ajailani.composeexperiment.ui.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.ajailani.composeexperiment.ui.navigation.Graph
import com.ajailani.composeexperiment.ui.navigation.Screen
import com.ajailani.composeexperiment.ui.screen.detail.DetailScreen
import com.ajailani.composeexperiment.ui.screen.detail.DetailViewModel
import com.ajailani.composeexperiment.ui.screen.home.HomeScreen
import com.ajailani.composeexperiment.ui.screen.home.HomeViewModel
import com.ajailani.composeexperiment.ui.screen.movies.MoviesRoute
import com.ajailani.composeexperiment.ui.screen.movies.MoviesViewModel
import com.ajailani.composeexperiment.ui.screen.timer.Timer1Screen
import com.ajailani.composeexperiment.ui.screen.timer.Timer2Screen
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.homeGraph(navController: NavController) {
    navigation(startDestination = Screen.Home.route, route = Graph.Home.route) {
        composable(route = "timer1") {
            Timer1Screen(navController)
        }

        composable(route = "timer2") {
            Timer2Screen()
        }

        composable(
            route = Screen.Home.route + "/{fullName}",
            arguments = listOf(
                navArgument("fullName") {
                    type = NavType.StringType
                }
            )
        ) {
            val homeViewModel = koinViewModel<HomeViewModel>()

            with(homeViewModel) {
                HomeScreen(
                    fullName = fullName,
                    onNavigateToMovies = { navController.navigate(Screen.Movies.route) }
                )
            }
        }

        composable(Screen.Movies.route) {
            val moviesViewModel = koinViewModel<MoviesViewModel>()

            MoviesRoute(
                moviesViewModel = moviesViewModel,
                onNavigateToDetail = {
                    navController.navigate(Screen.Detail.route + "/test")
                }
            )
        }

        composable(
            route = Screen.Detail.route + "/{fullName}",
            arguments = listOf(
                navArgument("fullName") {
                    type = NavType.StringType
                }
            )
        ) {
            val detailViewModel = koinViewModel<DetailViewModel>()

            DetailScreen(detailViewModel.fullName)
        }
    }
}