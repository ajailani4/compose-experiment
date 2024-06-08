package com.ajailani.composeexperiment.ui.screen.movies

sealed class MoviesEvent {
    data object GetMovies : MoviesEvent()
    data object PostSomething : MoviesEvent()
}
