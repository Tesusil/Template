package com.tesusil.template.domain.di

import com.tesusil.template.domain.repositories.UserRepository
import com.tesusil.template.domain.UseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {
} 