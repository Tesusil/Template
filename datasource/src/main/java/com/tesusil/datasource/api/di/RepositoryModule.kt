package com.tesusil.datasource.api.di

import com.tesusil.datasource.OnlineRepository
import com.tesusil.datasource.api.repositories.UserRepositoryImpl
import com.tesusil.template.domain.repositories.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindUserRepository(
        impl: UserRepositoryImpl
    ): UserRepository

    @Binds
    abstract fun bindOnlineRepository(
        impl: UserRepositoryImpl
    ): OnlineRepository
} 