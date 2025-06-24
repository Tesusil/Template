package com.tesusil.datasource.api.repositories

import com.tesusil.datasource.OnlineRepository
import com.tesusil.datasource.api.endpoints.UserEndpoints
import com.tesusil.datasource.api.mappers.ApiUserMapper
import com.tesusil.datasource.fetch
import com.tesusil.template.domain.Result
import com.tesusil.template.domain.models.User
import com.tesusil.template.domain.repositories.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userEndpoints: UserEndpoints,
    private val userMapper: ApiUserMapper,
    private val dispatcher: CoroutineDispatcher
) : UserRepository, OnlineRepository{

    private val userList: MutableStateFlow<Result<List<User>>> = MutableStateFlow(
        Result.success(emptyList())
    )

    override suspend fun getAllUsers(): Flow<Result<List<User>>> = userList

    override suspend fun refreshAllUsersInformation(): Result<Unit> {
        val result = fetch(
            call = userEndpoints.getAllUsers(),
            dispatcher = dispatcher
        ).map { userMapper.mapToDomainModelList(it) }
            .process(
                onSuccess = { userList.value = Result.success(it) },
                onError = {}
            )
        return result.toUnit()
    }

    override suspend fun getUserInformationById(userId: String): Result<User> {
        val currentListResult = userList.value
        val userList = currentListResult.getOrNull()
            ?: return Result.failure(IllegalStateException("Current user list is fill with error state"))
        val user = userList.firstOrNull { it.userId == userId }
            ?: return Result.failure(NoSuchElementException("Cannot find user with id: $userId"))
        return Result.success(user)
    }

    override suspend fun createRandomUser(): Result<String> {
        return fetch(
            call = userEndpoints.createNewRandomUser(),
            dispatcher = dispatcher
        ).map {
            it.userId
        }
    }

    override suspend fun updateUser(updatedUser: User): Result<String> {
        val dataModelUser = userMapper.mapToDataModel(updatedUser)
        return fetch(
            call = userEndpoints.updateUserById(updatedUser.userId, dataModelUser),
            dispatcher = dispatcher
        ).map {
            updatedUser.userId
        }
    }

    override suspend fun deleteUserById(userId: String): Result<Boolean> {
        return fetch(
            call = userEndpoints.deleteUserById(userId),
            dispatcher = dispatcher
        ).map { true }
    }
}