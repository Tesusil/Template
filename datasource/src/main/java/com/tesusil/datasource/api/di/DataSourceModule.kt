package com.tesusil.datasource.api.di

import com.tesusil.datasource.OnlineRepository
import com.tesusil.datasource.api.RetrofitClient
import com.tesusil.datasource.api.endpoints.UserEndpoints
import com.tesusil.datasource.api.mappers.ApiUserMapper
import com.tesusil.datasource.api.repositories.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    @Provides
    fun providesDefaultRetrofitClient(): RetrofitClient = RetrofitClient()

    @Provides
    fun providesDefaultRetrofit(retrofitClient: RetrofitClient): Retrofit = retrofitClient.client()

    @Provides
    fun providesUsersEndpoints(retrofit: Retrofit): UserEndpoints =
        retrofit.create(UserEndpoints::class.java)

    @Provides
    fun provideUserRepository(
        userEndpoints: UserEndpoints,
        userMapper: ApiUserMapper,
        dispatcher: CoroutineDispatcher
    ) = UserRepositoryImpl(userEndpoints, userMapper, dispatcher)

    @Provides
    fun provideOnlineRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): OnlineRepository = userRepositoryImpl
}