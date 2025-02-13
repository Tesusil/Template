package com.tesusil.datasource.api.comparators

import com.tesusil.datasource.api.UserTestData
import com.tesusil.datasource.api.models.UserApiModel
import com.tesusil.template.domain.models.User
import junit.framework.TestCase.assertFalse
import org.junit.Test
import java.time.Instant
import java.util.Date

/**
 * @see UserComparator
 */
class UserComparatorTest {

    private val comparator: UserComparator = UserComparator()

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
        assert(result)
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
        assert(result)
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
        assert(result)
    }
}