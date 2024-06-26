package com.ajailani.composeexperiment.data.repository

import com.ajailani.composeexperiment.data.api.MovieService
import com.ajailani.composeexperiment.data.model.MovieListResponse
import com.ajailani.composeexperiment.util.ApiResult
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class MovieRepository(private val movieService: MovieService) {
    fun getMovieList(page: Int) = flow {
        val response = movieService.getMovieList(page)

        when (response.status) {
            HttpStatusCode.OK -> {
                val movieListResponse = response.body<MovieListResponse>()

                emit(ApiResult.Success(movieListResponse))
            }

            HttpStatusCode.InternalServerError -> {
                emit(ApiResult.Error("Server error"))
            }

            else -> emit(ApiResult.Error("Sorry, something wrong happened"))
        }
    }

    fun postSomething() = flow {
        delay(2000L)
        emit(ApiResult.Success("Success"))
    }
}