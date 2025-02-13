package com.tesusil.datasource.api

import com.tesusil.datasource.api.models.UserApiModel
import com.tesusil.template.domain.models.User
import java.time.Instant
import java.util.Date

object UserTestData {

    private const val LIST_SIZE = 10;

    val DOMAIN_USER_MODEL: User = User(
        userId = "1",
        userName = "Domain User",
        avatarUrl = "",
        dateOfCreation = Date.from(Instant.now())
    )

    val DATA_USER_MODEL: UserApiModel = UserApiModel(
        userId = "0",
        userName = "Data User",
        avatarUrl = "",
        dateOfCreation = Date.from(Instant.now())
    )

    val DOMAIN_USER_LIST = List(size = LIST_SIZE) { index: Int ->
        User(
            userId = index.toString(),
            userName = "Domain User $index",
            avatarUrl = "",
            dateOfCreation = Date.from(Instant.now())
        )
    }

    val API_USER_LIST = List(size = LIST_SIZE) { index ->
        UserApiModel(
            userId = index.toString(),
            userName = "Data User $index",
            avatarUrl = "",
            dateOfCreation = Date.from(Instant.now())
        )
    }

}