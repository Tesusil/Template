package com.tesusil.template.core.ui

interface UiState {
    val isLoading: Boolean
    val error: Throwable?
}