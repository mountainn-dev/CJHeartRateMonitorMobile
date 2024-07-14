package com.san.heartratemonitormobile.domain.state

sealed class UiState {
    object Success: UiState()
    object Loading: UiState()
    object ServiceError: UiState()
    object Timeout: UiState()
}