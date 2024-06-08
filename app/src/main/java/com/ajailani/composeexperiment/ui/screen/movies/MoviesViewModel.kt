package com.ajailani.composeexperiment.ui.screen.movies

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajailani.composeexperiment.data.model.Movie
import com.ajailani.composeexperiment.data.repository.MovieRepository
import com.ajailani.composeexperiment.util.OneTimeEvent
import com.ajailani.composeexperiment.util.collectApiResult
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

class MoviesViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    private val _moviesUiState = MutableStateFlow(MoviesUiState())
    val moviesUiState = _moviesUiState.asStateFlow()

    private val _oneTimeEvent = Channel<OneTimeEvent>()
    val oneTimeEvent = _oneTimeEvent.receiveAsFlow()

    val moviesSnapshot = mutableStateListOf<Movie>()

    init {
        getMovieList(1)
    }

    fun onEvent(event: MoviesEvent) {
        when (event) {
            MoviesEvent.GetMovies -> getMovieList(moviesUiState.value.moviesPage + 1)

            MoviesEvent.PostSomething -> postSomething()
        }
    }

    private fun getMovieList(page: Int) {
        _moviesUiState.update {
            it.copy(
                loading = true,
                isPaginationLoading = page > 1
            )
        }

        viewModelScope.collectApiResult(
            useCase = movieRepository.getMovieList(page),
            onSuccess = { data, commonUiState ->
                _moviesUiState.update {
                    it.copy(
                        loading = false,
                        isPaginationLoading = false,
                        moviesPage = data.page,
                        movies = data.results,
                        commonUiState = commonUiState
                    )
                }

                _oneTimeEvent.send(OneTimeEvent.ShowToastEvent("Success"))
            },
            onError = { message, commonUiState ->
                _moviesUiState.update {
                    it.copy(
                        loading = false,
                        isPaginationLoading = false,
                        commonUiState = commonUiState
                    )
                }

                _oneTimeEvent.send(OneTimeEvent.ShowToastEvent(message))
            }
        )
    }

    private fun postSomething() {
        viewModelScope.collectApiResult(
            useCase = movieRepository.postSomething(),
            onSuccess = { _, _ ->
                _oneTimeEvent.send(OneTimeEvent.NavigateEvent("route"))
            },
            onError = { message, _ ->
                _oneTimeEvent.send(OneTimeEvent.ShowToastEvent(message))
            }
        )
    }
}