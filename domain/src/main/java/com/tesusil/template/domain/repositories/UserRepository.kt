package com.tesusil.template.domain.repositories

import com.tesusil.template.domain.models.User
import kotlinx.coroutines.flow.Flow
import com.tesusil.template.domain.Result

interface UserRepository {
    suspend fun getAllUsers(): Flow<Result<List<User>>>
    suspend fun refreshAllUsersInformation(): Result<Unit>
    suspend fun getUserInformationById(userId: String): Result<User>
    suspend fun createRandomUser(): Result<String>
    suspend fun updateUser(updatedUser: User): Result<String>
    suspend fun deleteUserById(userId: String): Result<Boolean>
}