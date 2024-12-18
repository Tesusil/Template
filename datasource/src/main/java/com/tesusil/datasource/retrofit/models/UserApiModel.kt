package com.tesusil.datasource.retrofit.models

import com.google.gson.annotations.SerializedName
import java.util.Date

data class UserApiModel(
    @SerializedName("name") private val userName: String,
    @SerializedName("avatar") private val avatarUrl: String,
    @SerializedName("id") private val userId: String,
    @SerializedName("createdAt") private val dateOfCreation: Date
)

