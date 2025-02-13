package com.tesusil.datasource.api.di

import com.tesusil.datasource.api.RetrofitClient
import com.tesusil.datasource.api.endpoints.UserEndpoints
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    @Provides
    fun providesDefaultRetrofitClient(): RetrofitClient = RetrofitClient()

    @Provides
    fun providesDefaultRetrofit(retrofitClient: RetrofitClient): Retrofit = retrofitClient.client()

    @Provides
    fun providesUsersEndpoints(retrofit: Retrofit): UserEndpoints = retrofit.create(UserEndpoints::class.java)
}