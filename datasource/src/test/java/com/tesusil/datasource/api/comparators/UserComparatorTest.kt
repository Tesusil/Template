package com.tesusil.datasource.api.comparators

import com.tesusil.datasource.api.UserTestData
import com.tesusil.datasource.api.models.UserApiModel
import com.tesusil.template.domain.models.User
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.Instant
import java.util.Date

/**
 * @see UserComparator
 */
class UserComparatorTest {

    private val comparator = UserComparator()

    @Test
    fun `should return false when domain model is null`() {
        //GIVEN:
        val domainUser: User? = null
        val apiUser: UserApiModel = UserTestData.DATA_USER_MODEL

        //WHEN:
        val result = comparator.compare(domainUser, apiUser)

        //THEN:
        assertFalse(result)
    }

    @Test
    fun `should return false when data model is null`() {
        //GIVEN:
        val domainUser: User = UserTestData.DOMAIN_USER_MODEL
        val apiUser: UserApiModel? = null

        //WHEN:
        val result = comparator.compare(domainUser, apiUser)

        //THEN:
        assertFalse(result)
    }

    @Test
    fun `should return true when both models are the same`() {
        //GIVEN:
        val domainUser: User = UserTestData.DOMAIN_USER_MODEL
        val apiUser = UserApiModel(
            userId = domainUser.userId,
            userName = domainUser.userName,
            avatarUrl = domainUser.avatarUrl,
            dateOfCreation = domainUser.dateOfCreation
        )

        //When:
        val result = comparator.compare(domainUser, apiUser)

        //Then:
        assertTrue(result)
    }

    @Test
    fun `return false when models have different userId`() {
        val domainUser: User = UserTestData.DOMAIN_USER_MODEL
        val apiUser = UserApiModel(
            userId = "",
            userName = domainUser.userName,
            avatarUrl = domainUser.avatarUrl,
            dateOfCreation = domainUser.dateOfCreation
        )

        //When:
        val result = comparator.compare(domainUser, apiUser)

        //Then:
        assertFalse(result)
    }

    @Test
    fun `should return false when models have different names`() {
        val domainUser: User = UserTestData.DOMAIN_USER_MODEL
        val apiUser = UserApiModel(
            userId = domainUser.userId,
            userName = "",
            avatarUrl = domainUser.avatarUrl,
            dateOfCreation = domainUser.dateOfCreation
        )

        //When:
        val result = comparator.compare(domainUser, apiUser)

        //Then:
        assertFalse(result)
    }

    @Test
    fun `should return false when models have different avatar urls`() {
        val domainUser: User = UserTestData.DOMAIN_USER_MODEL
        val apiUser: UserApiModel = UserApiModel(
            userId = domainUser.userId,
            userName = domainUser.userName,
            avatarUrl = "",
            dateOfCreation = domainUser.dateOfCreation
        )

        //When:
        val result = comparator.compare(domainUser, apiUser)

        //Then:
        assertFalse(result)
    }

    @Test
    fun `should return false when models have diffrent creation dates`() {
        val domainUser: User = UserTestData.DOMAIN_USER_MODEL
        val apiUser = UserApiModel(
            userId = domainUser.userId,
            userName = domainUser.userName,
            avatarUrl = domainUser.avatarUrl,
            dateOfCreation = Date.from(Instant.now())
        )

        //When:
        val result = comparator.compare(domainUser, apiUser)

        //Then:
        assertFalse(result)
    }

    @Test
    fun `compare should return true when objects have same values`() {
        // Given
        val now = Date()
        val user = User(
            userName = "testUser",
            avatarUrl = "https://example.com/avatar.jpg",
            userId = "user123",
            dateOfCreation = now
        )
        
        val apiModel = UserApiModel(
            userName = "testUser",
            avatarUrl = "https://example.com/avatar.jpg",
            userId = "user123",
            dateOfCreation = now
        )
        
        // When
        val result = comparator.compare(user, apiModel)
        
        // Then
        assertTrue(result)
    }
    
    @Test
    fun `compare should return false when userId is different`() {
        // Given
        val now = Date()
        val user = User(
            userName = "testUser",
            avatarUrl = "https://example.com/avatar.jpg",
            userId = "user123",
            dateOfCreation = now
        )
        
        val apiModel = UserApiModel(
            userName = "testUser",
            avatarUrl = "https://example.com/avatar.jpg",
            userId = "differentId",
            dateOfCreation = now
        )
        
        // When
        val result = comparator.compare(user, apiModel)
        
        // Then
        assertFalse(result)
    }
    
    @Test
    fun `compare should return false when userName is different`() {
        // Given
        val now = Date()
        val user = User(
            userName = "testUser",
            avatarUrl = "https://example.com/avatar.jpg",
            userId = "user123",
            dateOfCreation = now
        )
        
        val apiModel = UserApiModel(
            userName = "differentName",
            avatarUrl = "https://example.com/avatar.jpg",
            userId = "user123",
            dateOfCreation = now
        )
        
        // When
        val result = comparator.compare(user, apiModel)
        
        // Then
        assertFalse(result)
    }
    
    @Test
    fun `compare should return false when avatarUrl is different`() {
        // Given
        val now = Date()
        val user = User(
            userName = "testUser",
            avatarUrl = "https://example.com/avatar.jpg",
            userId = "user123",
            dateOfCreation = now
        )
        
        val apiModel = UserApiModel(
            userName = "testUser",
            avatarUrl = "https://example.com/different.jpg",
            userId = "user123",
            dateOfCreation = now
        )
        
        // When
        val result = comparator.compare(user, apiModel)
        
        // Then
        assertFalse(result)
    }
    
    @Test
    fun `compare should return false when dateOfCreation is different`() {
        // Given
        val now = Date()
        val yesterday = Date(now.time - 24 * 60 * 60 * 1000)
        val user = User(
            userName = "testUser",
            avatarUrl = "https://example.com/avatar.jpg",
            userId = "user123",
            dateOfCreation = now
        )
        
        val apiModel = UserApiModel(
            userName = "testUser",
            avatarUrl = "https://example.com/avatar.jpg",
            userId = "user123",
            dateOfCreation = yesterday
        )
        
        // When
        val result = comparator.compare(user, apiModel)
        
        // Then
        assertFalse(result)
    }
    
    @Test
    fun `compare should return false when user is null`() {
        // Given
        val apiModel = UserApiModel(
            userName = "testUser",
            avatarUrl = "https://example.com/avatar.jpg",
            userId = "user123",
            dateOfCreation = Date()
        )
        
        // When
        val result = comparator.compare(null, apiModel)
        
        // Then
        assertFalse(result)
    }
    
    @Test
    fun `compare should return false when apiModel is null`() {
        // Given
        val user = User(
            userName = "testUser",
            avatarUrl = "https://example.com/avatar.jpg",
            userId = "user123",
            dateOfCreation = Date()
        )
        
        // When
        val result = comparator.compare(user, null)
        
        // Then
        assertFalse(result)
    }
    
    @Test
    fun `compare should return false when both objects are null`() {
        // When
        val result = comparator.compare(null, null)
        
        // Then
        assertFalse(result)
    }
}