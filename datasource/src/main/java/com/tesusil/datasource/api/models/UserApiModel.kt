package com.tesusil.datasource.api.models


import com.google.gson.annotations.SerializedName
import java.util.Date

data class UserApiModel(
    @SerializedName("name") val userName: String,
    @SerializedName("avatar") val avatarUrl: String,
    @SerializedName("id") val userId: String,
    @SerializedName("createdAt") val dateOfCreation: Date
)

