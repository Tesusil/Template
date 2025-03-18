package com.tesusil.datasource.api.repositories

import com.tesusil.datasource.api.endpoints.UserEndpoints
import com.tesusil.datasource.api.mappers.ApiUserMapper
import com.tesusil.datasource.api.models.UserApiModel
import com.tesusil.template.domain.Result
import com.tesusil.template.domain.models.User
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import retrofit2.Call
import retrofit2.Response
import java.util.Date
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
@Config(application = HiltTestApplication::class)
@RunWith(RobolectricTestRunner::class)
class UserRepositoryImplTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var userEndpoints: UserEndpoints

    @Inject
    lateinit var userMapper: ApiUserMapper

    @Inject
    lateinit var testDispatcher: CoroutineDispatcher

    private lateinit var userRepository: UserRepositoryImpl

    @Before
    fun setup() {
        hiltRule.inject()
        Dispatchers.setMain(testDispatcher as StandardTestDispatcher)
        userRepository = UserRepositoryImpl(
            userEndpoints = userEndpoints,
            userMapper = userMapper,
            dispatcher = testDispatcher
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `refreshAllUsersInformation should update user list on success`() = runTest {
        // Given
        val apiUsers = listOf(
            createApiUserModel("1"),
            createApiUserModel("2")
        )
        val domainUsers = listOf(
            createUser("1"),
            createUser("2")
        )
        val usersCall = mockk<Call<List<UserApiModel>>>()
        val usersResponse = Response.success(apiUsers)

        every { userEndpoints.getAllUsers() } returns usersCall
        every { usersCall.execute() } returns usersResponse
        every { userMapper.mapToDomainModelList(apiUsers) } returns domainUsers

        // When
        val result = userRepository.refreshAllUsersInformation()

        // Then
        assertTrue(result.isSuccessful())
        val flowResult = userRepository.getAllUsers().first()
        assertTrue(flowResult.isSuccessful())
        assertEquals(domainUsers, flowResult.getOrNull())
        
        // Verify interactions with dependencies
        coVerify { userEndpoints.getAllUsers() }
        coVerify { userMapper.mapToDomainModelList(apiUsers) }
    }

    @Test
    fun `getUserInformationById should return user when exists`() = runTest {
        // Given
        val userId = "123"
        val user = createUser(userId)
        val users = listOf(user, createUser("456"))
        
        // Setup initial state in the repository
        val apiUsers = listOf(
            createApiUserModel(userId),
            createApiUserModel("456")
        )
        val usersCall = mockk<Call<List<UserApiModel>>>()
        val usersResponse = Response.success(apiUsers)

        every { userEndpoints.getAllUsers() } returns usersCall
        every { usersCall.execute() } returns usersResponse
        every { userMapper.mapToDomainModelList(apiUsers) } returns users

        userRepository.refreshAllUsersInformation()
        (testDispatcher as StandardTestDispatcher).scheduler.advanceUntilIdle()

        // When
        val result = userRepository.getUserInformationById(userId)

        // Then
        assertTrue(result.isSuccessful())
        assertEquals(user, result.getOrNull())
    }

    @Test
    fun `createRandomUser should return userId on success`() = runTest {
        // Given
        val newUserId = "new-user-id"
        val apiUser = createApiUserModel(newUserId)
        val createUserCall = mockk<Call<UserApiModel>>()
        val createUserResponse = Response.success(apiUser)

        every { userEndpoints.createNewRandomUser() } returns createUserCall
        every { createUserCall.execute() } returns createUserResponse

        // When
        val result = userRepository.createRandomUser()

        // Then
        assertTrue(result.isSuccessful())
        assertEquals(newUserId, result.getOrNull())
    }

    @Test
    fun `updateUser should return userId on success`() = runTest {
        // Given
        val user = createUser("update-id")
        val apiUser = createApiUserModel("update-id")
        val updateUserCall = mockk<Call<UserApiModel>>()
        val updateUserResponse = Response.success(apiUser)

        every { userMapper.mapToDataModel(user) } returns apiUser
        every { userEndpoints.updateUserById(user.userId, apiUser) } returns updateUserCall
        every { updateUserCall.execute() } returns updateUserResponse

        // When
        val result = userRepository.updateUser(user)

        // Then
        assertTrue(result.isSuccessful())
        assertEquals(user.userId, result.getOrNull())
    }

    @Test
    fun `deleteUserById should return true on success`() = runTest {
        // Given
        val userId = "delete-id"
        val deleteUserCall = mockk<Call<Unit>>()
        val deleteUserResponse = Response.success(Unit)

        every { userEndpoints.deleteUserById(userId) } returns deleteUserCall
        every { deleteUserCall.execute() } returns deleteUserResponse

        // When
        val result = userRepository.deleteUserById(userId)

        // Then
        assertTrue(result.isSuccessful())
        assertEquals(true, result.getOrNull())
    }

    private fun createApiUserModel(id: String) = UserApiModel(
        userName = "User $id",
        avatarUrl = "https://example.com/avatar/$id",
        userId = id,
        dateOfCreation = Date()
    )

    private fun createUser(id: String) = User(
        userName = "User $id",
        avatarUrl = "https://example.com/avatar/$id",
        userId = id,
        dateOfCreation = Date()
    )
} 