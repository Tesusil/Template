package com.tesusil.home

import com.tesusil.template.core.ui.UiState
import com.tesusil.template.domain.models.User

// Stan ekranu Home
data class HomeState(
    override val isLoading: Boolean = false,
    override val error: Throwable? = null,
    val users: List<User> = emptyList()
) : UiState 