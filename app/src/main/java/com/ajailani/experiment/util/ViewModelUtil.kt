package com.ajailani.experiment.util

import com.ajailani.experiment.ui.screen.movies.CommonUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun <T> CoroutineScope.collectApiResult(
    useCase: Flow<ApiResult<T>>,
    onSuccess: suspend (data: T, commonUiState: CommonUiState) -> Unit,
    onError: suspend (errorMessage: String?, commonUiState: CommonUiState) -> Unit
) {
    this.launch {
        useCase.collect { result ->
            when (result) {
                is ApiResult.Success -> {
                    result.data?.let {
                        onSuccess(
                            it,
                            CommonUiState(
                                showCentralLoading = false,
                                errorMessage = null
                            )
                        )
                    }
                }

                is ApiResult.Error -> {
                    result.message?.let {
                        onError(
                            it,
                            CommonUiState(
                                showCentralLoading = false,
                                errorMessage = null
                            )
                        )
                    }
                }
            }
        }
    }
}