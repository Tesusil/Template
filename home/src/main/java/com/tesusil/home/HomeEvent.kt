package com.tesusil.home

import com.tesusil.template.domain.models.User

sealed class HomeEvent {
    object LoadUsers : HomeEvent()
    data class RefreshUsers(val force: Boolean = false) : HomeEvent()
    data class SelectUser(val userId: String) : HomeEvent()
    data class DeleteUser(val userId: String) : HomeEvent()
    object CreateRandomUser : HomeEvent()
    data class UpdateUser(val user: User) : HomeEvent()
} 