package com.tesusil.datasource.api.models

import androidx.annotation.RestrictTo
import com.google.gson.annotations.SerializedName
import java.util.Date

@RestrictTo(RestrictTo.Scope.LIBRARY)
data class UserApiModel(
    @SerializedName("name") val userName: String,
    @SerializedName("avatar") val avatarUrl: String,
    @SerializedName("id") val userId: String,
    @SerializedName("createdAt") val dateOfCreation: Date
)

