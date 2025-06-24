package com.tesusil.home

import android.content.Context
import androidx.lifecycle.ViewModel
import com.tesusil.template.core.launchWithIO
import com.tesusil.template.core.setState
import com.tesusil.template.domain.models.User
import com.tesusil.template.domain.repositories.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel(
    context: Context
    // private val userRepository: UserRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state

    fun createInitialState(): HomeState = HomeState()

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.LoadUsers -> loadUsers()
            is HomeEvent.RefreshUsers -> refreshUsers()
            is HomeEvent.SelectUser -> selectUser(event.userId)
            is HomeEvent.DeleteUser -> deleteUser(event.userId)
            is HomeEvent.CreateRandomUser -> createRandomUser()
            is HomeEvent.UpdateUser -> updateUser(event.user)
        }
    }

    private fun loadUsers() {
        _state.setState { copy(isLoading = true, error = null) }
        launchWithIO {
//            userRepository.getAllUsers().collect { result ->
//                result.process(
//                    onSuccess = { users -> _state.setState { copy(users = users, isLoading = false, error = null) } },
//                    onError = { throwable -> _state.setState { copy(isLoading = false, error = throwable as? Exception ?: Exception(throwable)) } }
//                )
//            }
        }
    }

    private fun refreshUsers() {
        _state.setState { copy(isLoading = true, error = null) }
        launchWithIO {
//            val result = userRepository.refreshAllUsersInformation()
//            result.process(
//                onSuccess = { loadUsers() },
//                onError = { throwable -> _state.setState { copy(isLoading = false, error = throwable as? Exception ?: Exception(throwable)) } }
//            )
        }
    }

    private fun selectUser(userId: String) {
        //TODO: navigate to user details
    }

    private fun deleteUser(userId: String) {
        _state.setState { copy(isLoading = true, error = null) }
        launchWithIO {
//            val result = userRepository.deleteUserById(userId)
//            result.process(
//                onSuccess = { refreshUsers() },
//                onError = { throwable -> _state.setState { copy(isLoading = false, error = throwable as? Exception ?: Exception(throwable)) } }
//            )
        }
    }

    private fun createRandomUser() {
        _state.setState { copy(isLoading = true, error = null) }
        launchWithIO {
//            val result = userRepository.createRandomUser()
//            result.process(
//                onSuccess = { refreshUsers() },
//                onError = { throwable -> _state.setState { copy(isLoading = false, error = throwable as? Exception ?: Exception(throwable)) } }
//            )
        }
    }

    private fun updateUser(user: User) {
        _state.setState { copy(isLoading = true, error = null) }
        launchWithIO {
//            val result = userRepository.updateUser(user)
//            result.process(
//                onSuccess = { refreshUsers() },
//                onError = { throwable -> _state.setState { copy(isLoading = false, error = throwable as? Exception ?: Exception(throwable)) } }
//            )
        }
    }
} 