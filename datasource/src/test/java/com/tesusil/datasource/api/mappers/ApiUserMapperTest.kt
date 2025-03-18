package com.tesusil.datasource.api.mappers

import com.tesusil.datasource.api.UserTestData
import com.tesusil.datasource.api.comparators.UserComparator
import com.tesusil.datasource.api.models.UserApiModel
import com.tesusil.template.domain.models.User
import io.mockk.junit4.MockKRule
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.assertEquals
import java.util.Date

/**
 * @see ApiUserMapper
 */
class ApiUserMapperTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    private val comparator = UserComparator()
    private val mapper = ApiUserMapper()

    @Test
    fun `mapToDomainModel should correctly map UserApiModel to User`() {
        // Given
        val now = Date()
        val apiModel = UserApiModel(
            userName = "testUser",
            avatarUrl = "https://example.com/avatar.jpg",
            userId = "user123",
            dateOfCreation = now
        )

        // When
        val result = mapper.mapToDomainModel(apiModel)

        // Then
        assertEquals("testUser", result.userName)
        assertEquals("https://example.com/avatar.jpg", result.avatarUrl)
        assertEquals("user123", result.userId)
        assertEquals(now, result.dateOfCreation)
    }

    @Test
    fun `mapToDataModel should correctly map User to UserApiModel`() {
        // Given
        val now = Date()
        val domainModel = User(
            userName = "testUser",
            avatarUrl = "https://example.com/avatar.jpg",
            userId = "user123",
            dateOfCreation = now
        )

        // When
        val result = mapper.mapToDataModel(domainModel)

        // Then
        assertEquals("testUser", result.userName)
        assertEquals("https://example.com/avatar.jpg", result.avatarUrl)
        assertEquals("user123", result.userId)
        assertEquals(now, result.dateOfCreation)
    }

    @Test
    fun `mapToDomainModelList should correctly map list of UserApiModel to list of User`() {
        // Given
        val now = Date()
        val apiModelList = listOf(
            UserApiModel("user1", "https://example.com/avatar1.jpg", "id1", now),
            UserApiModel("user2", "https://example.com/avatar2.jpg", "id2", now)
        )

        // When
        val result = mapper.mapToDomainModelList(apiModelList)

        // Then
        assertEquals(2, result.size)
        
        assertEquals("user1", result[0].userName)
        assertEquals("https://example.com/avatar1.jpg", result[0].avatarUrl)
        assertEquals("id1", result[0].userId)
        assertEquals(now, result[0].dateOfCreation)
        
        assertEquals("user2", result[1].userName)
        assertEquals("https://example.com/avatar2.jpg", result[1].avatarUrl)
        assertEquals("id2", result[1].userId)
        assertEquals(now, result[1].dateOfCreation)
    }

    @Test
    fun `mapToDataModelList should correctly map list of User to list of UserApiModel`() {
        // Given
        val now = Date()
        val domainModelList = listOf(
            User("user1", "https://example.com/avatar1.jpg", "id1", now),
            User("user2", "https://example.com/avatar2.jpg", "id2", now)
        )

        // When
        val result = mapper.mapToDataModelList(domainModelList)

        // Then
        assertEquals(2, result.size)
        
        assertEquals("user1", result[0].userName)
        assertEquals("https://example.com/avatar1.jpg", result[0].avatarUrl)
        assertEquals("id1", result[0].userId)
        assertEquals(now, result[0].dateOfCreation)
        
        assertEquals("user2", result[1].userName)
        assertEquals("https://example.com/avatar2.jpg", result[1].avatarUrl)
        assertEquals("id2", result[1].userId)
        assertEquals(now, result[1].dateOfCreation)
    }
}

