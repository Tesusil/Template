package com.tesusil.template.domain.models

import java.util.Date

data class User(
    val userName: String,
    val avatarUrl: String,
    val userId: String,
    val dateOfCreation: Date,
)
