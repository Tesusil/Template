package com.tesusil.datasource.retrofit.endpoints

import com.tesusil.datasource.retrofit.models.UserApiModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface UserEndpoints {
    @GET("users")
    fun getAllUsers(): Call<List<UserApiModel>>

    @GET("users/{id}")
    fun getUserById(@Path("id") userId: String): Call<UserApiModel>

    @POST("users")
    fun createNewRandomUser(): Call<UserApiModel>

    @PUT("users/{id}")
    fun updateUserById(@Path("id") userId: String, @Body newUserData: UserApiModel): Call<UserApiModel>

    @DELETE("users/{id}")
    fun deleteUserById(@Path("id") userId: String)
}