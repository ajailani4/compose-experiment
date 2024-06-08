package com.ajailani.composeexperiment.data.api

import io.ktor.client.HttpClient
import io.ktor.client.request.get

class MovieService(private val httpClient: HttpClient) {
    suspend fun getMovieList(page: Int) = httpClient.get("discover/movie") {
        url {
            parameters.append("api_key", "17780b9de913a30c27ac79044c1eb633")
            parameters.append("page", page.toString())
        }
    }
}