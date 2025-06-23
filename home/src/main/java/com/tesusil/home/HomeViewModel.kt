package com.tesusil.home

import androidx.lifecycle.viewModelScope
import com.tesusil.template.core.BaseViewModel
import com.tesusil.datasource.OnlineRepository
import com.tesusil.template.domain.repositories.UserRepository
import com.tesusil.template.domain.models.User
import com.tesusil.template.domain.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val onlineRepository: OnlineRepository
) : BaseViewModel<HomeState, HomeEvent>() {

    override fun createInitialState(): HomeState = HomeState()

    override fun onEvent(event: HomeEvent) {
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
        setState { copy(isLoading = true, error = null) }
        launchWithIO {
            userRepository.getAllUsers().collect { result ->
                result.process(
                    onSuccess = { users -> setState { copy(users = users, isLoading = false, error = null) } },
                    onError = { throwable -> setState { copy(isLoading = false, error = throwable as? Exception ?: Exception(throwable)) } }
                )
            }
        }
    }

    private fun refreshUsers() {
        setState { copy(isLoading = true, error = null) }
        launchWithIO {
            val result = userRepository.refreshAllUsersInformation()
            result.process(
                onSuccess = { loadUsers() },
                onError = { throwable -> setState { copy(isLoading = false, error = throwable as? Exception ?: Exception(throwable)) } }
            )
        }
    }

    private fun selectUser(userId: String) {
        //TODO: navigate to user details
    }

    private fun deleteUser(userId: String) {
        setState { copy(isLoading = true, error = null) }
        launchWithIO {
            val result = userRepository.deleteUserById(userId)
            result.process(
                onSuccess = { refreshUsers() },
                onError = { throwable -> setState { copy(isLoading = false, error = throwable as? Exception ?: Exception(throwable)) } }
            )
        }
    }

    private fun createRandomUser() {
        setState { copy(isLoading = true, error = null) }
        launchWithIO {
            val result = userRepository.createRandomUser()
            result.process(
                onSuccess = { refreshUsers() },
                onError = { throwable -> setState { copy(isLoading = false, error = throwable as? Exception ?: Exception(throwable)) } }
            )
        }
    }

    private fun updateUser(user: User) {
        setState { copy(isLoading = true, error = null) }
        launchWithIO {
            val result = userRepository.updateUser(user)
            result.process(
                onSuccess = { refreshUsers() },
                onError = { throwable -> setState { copy(isLoading = false, error = throwable as? Exception ?: Exception(throwable)) } }
            )
        }
    }
} 