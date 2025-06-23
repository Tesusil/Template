package com.tesusil.datasource.api.di

import com.tesusil.datasource.api.comparators.UserComparator
import com.tesusil.datasource.api.endpoints.UserEndpoints
import com.tesusil.datasource.api.mappers.ApiUserMapper
import com.tesusil.datasource.api.repositories.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataSourceModule::class]
)
class TestDataSourceModule {

    private val testDispatcher = TestCoroutineDispatcher()

    @Provides
    @Singleton
    fun providesTestUserEndpoints(): UserEndpoints = mockk(relaxed = true)

    @Provides
    @Singleton
    fun providesTestApiUserMapper(): ApiUserMapper = mockk(relaxed = true)

    @Provides
    @Singleton
    fun providesTestDispatcher(): CoroutineDispatcher = testDispatcher

    @Provides
    @Singleton
    fun providesUserComparator(): UserComparator = UserComparator()

    @Provides
    @Singleton
    fun providesUserRepository(
        userEndpoints: UserEndpoints,
        userMapper: ApiUserMapper,
        dispatcher: CoroutineDispatcher
    ): UserRepositoryImpl = UserRepositoryImpl(
        userEndpoints = userEndpoints,
        userMapper = userMapper,
        dispatcher = dispatcher
    )
} 