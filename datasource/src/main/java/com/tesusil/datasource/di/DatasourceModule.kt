package com.tesusil.datasource.di

import com.tesusil.datasource.api.endpoints.UserEndpoints
import com.tesusil.datasource.api.mappers.ApiUserMapper
import com.tesusil.template.domain.repositories.UserRepository
import com.tesusil.datasource.api.repositories.UserRepositoryImpl
import com.tesusil.template.datasource.BuildConfig
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class DatasourceModule {

    companion object {
        @Provides
        @Singleton
        fun provideRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        @Provides
        @Singleton
        fun provideUserEndpoints(retrofit: Retrofit): UserEndpoints {
            return retrofit.create(UserEndpoints::class.java)
        }

        @Provides
        @Singleton
        fun provideUserRepository(
            userEndpoints: UserEndpoints,
            userMapper: ApiUserMapper,
            dispatcher: CoroutineDispatcher
        ): UserRepository = UserRepositoryImpl(userEndpoints, userMapper, dispatcher)
    }
}