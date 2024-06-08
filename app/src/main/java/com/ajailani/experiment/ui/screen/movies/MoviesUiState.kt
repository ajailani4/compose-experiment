package com.ajailani.experiment.ui.screen.movies

import com.ajailani.experiment.data.model.Movie

data class MoviesUiState(
    val loading: Boolean = false,
    val isPaginationLoading: Boolean = false,
    val moviesPage: Int = 0,
    val movies: List<Movie> = emptyList(),
    val commonUiState: CommonUiState = CommonUiState()
)


data class CommonUiState(
    val showCentralLoading: Boolean = false,
    val errorMessage: String? = null
)