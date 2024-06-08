package com.ajailani.composeexperiment.ui.screen.movies

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ajailani.composeexperiment.data.model.Movie
import com.ajailani.composeexperiment.util.MixpanelUtil
import com.ajailani.composeexperiment.util.OneTimeEventObserver
import com.ajailani.composeexperiment.util.PageViewTracker
import org.koin.androidx.compose.koinViewModel

@Composable
fun MoviesRoute(
    moviesViewModel: MoviesViewModel = koinViewModel(),
    onNavigateToDetail: () -> Unit
) {
    PageViewTracker("Movies")

    val context = LocalContext.current

    val moviesUiState = moviesViewModel.moviesUiState.collectAsStateWithLifecycle().value
    val moviesSnapshot = moviesViewModel.moviesSnapshot
    val oneTimeEvent = moviesViewModel.oneTimeEvent
    val onEvent = moviesViewModel::onEvent

    val lazyListState = rememberLazyListState()
    val canScrollForward by remember {
        derivedStateOf { lazyListState.canScrollForward }
    }
    
    LaunchedEffect(canScrollForward) {
        if (moviesUiState.movies.isNotEmpty() && !lazyListState.canScrollForward) {
            onEvent(MoviesEvent.GetMovies)
        }
    }

    with(moviesUiState) {
        when {
            !loading -> {
                LaunchedEffect(movies) {
                    moviesSnapshot.addAll(movies)
                }
            }

            loading -> {
                if (moviesPage == 0) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }

    MoviesScreen(
        lazyListState = lazyListState,
        movies = moviesSnapshot,
        isPaginationLoading = moviesUiState.isPaginationLoading,
        onItemClicked = { onEvent(MoviesEvent.PostSomething) }
    )

    OneTimeEventObserver(
        event = oneTimeEvent,
        onNavigate = {
            onNavigateToDetail()
        },
        onShowToast = { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    )
}

@Composable
fun MoviesScreen(
    lazyListState: LazyListState,
    movies: List<Movie>,
    isPaginationLoading: Boolean,
    onItemClicked: () -> Unit
) {
    Column {
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(vertical = 10.dp),
            state = lazyListState
        ) {
            items(movies) {
                UserItem(
                    movie = it,
                    onItemClicked = {
                        onItemClicked()

                        MixpanelUtil.track(
                            event = "Movie Item Clicked",
                            properties = mapOf(
                                "movieId" to it.id.toString(),
                                "movieTitle" to it.title
                            )
                        )
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }

        if (isPaginationLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
private fun UserItem(
    movie: Movie,
    onItemClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClicked() }
    ) {
        AsyncImage(
            modifier = Modifier
                .height(100.dp)
                .weight(1f),
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://image.tmdb.org/t/p/w500${movie.poster}")
                .build(),
            contentDescription = "Movie poster"
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            modifier = Modifier.weight(3f),
            text = movie.title
        )
    }
}