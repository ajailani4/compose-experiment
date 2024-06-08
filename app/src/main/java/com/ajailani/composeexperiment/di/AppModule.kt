package com.ajailani.composeexperiment.di

import android.util.Log
import com.ajailani.composeexperiment.data.api.MovieService
import com.ajailani.composeexperiment.data.local.PreferencesDataStore
import com.ajailani.composeexperiment.data.repository.MovieRepository
import com.ajailani.composeexperiment.ui.screen.detail.DetailViewModel
import com.ajailani.composeexperiment.ui.screen.experiment.DragAndDropViewModel
import com.ajailani.composeexperiment.ui.screen.home.HomeViewModel
import com.ajailani.composeexperiment.ui.screen.movies.MoviesViewModel
import com.ajailani.composeexperiment.ui.screen.timer.Timer1ViewModel
import com.ajailani.composeexperiment.ui.screen.timer.Timer2ViewModel
import com.mixpanel.android.mpmetrics.MixpanelAPI
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    single {
        HttpClient(Android) {
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.d("Ktor Log: ", message)
                    }
                }
            }

            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    ignoreUnknownKeys = true
                    encodeDefaults = true
                })
            }

            defaultRequest {
                url("https://api.themoviedb.org/3/")
            }
        }
    }

    single<MixpanelAPI> {
        MixpanelAPI.getInstance(androidContext(), "9f9616da92f19470bfb66a54a2f78f6d", true)
    }

    singleOf(::MovieService)
    singleOf(::MovieRepository)
    singleOf(::PreferencesDataStore)

    viewModelOf(::HomeViewModel)
    viewModelOf(::MoviesViewModel)
    viewModelOf(::DetailViewModel)
    viewModelOf(::DragAndDropViewModel)
    viewModelOf(::Timer1ViewModel)
    viewModelOf(::Timer2ViewModel)
}