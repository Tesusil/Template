package com.tesusil.datasource.api.repositories

import com.tesusil.datasource.api.endpoints.UserEndpoints
import com.tesusil.datasource.api.mappers.ApiUserMapper
import com.tesusil.datasource.api.models.UserApiModel
import com.tesusil.template.domain.models.User
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
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

@HiltAndroidTest
@Config(application = HiltTestApplication::class)
@RunWith(RobolectricTestRunner::class)
class UserRepositoryHiltTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var userRepository: UserRepositoryImpl

    @Inject
    lateinit var userEndpoints: UserEndpoints

    @Inject
    lateinit var userMapper: ApiUserMapper

    @Before
    fun setup() {
        hiltRule.inject()
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
        val usersCall = io.mockk.mockk<Call<List<UserApiModel>>>()
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