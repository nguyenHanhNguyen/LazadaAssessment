package com.lazada.di

import com.lazada.domain.GetFollowersUseCase
import com.lazada.domain.GetFollowersUseCaseImpl
import com.lazada.domain.GetUserUseCase
import com.lazada.domain.GetUserUseCaseImpl
import com.lazada.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ViewModelModule {

    @Singleton
    @Provides
    fun provideUserUseCase(
        userRepository: UserRepository
    ): GetUserUseCase {
        return GetUserUseCaseImpl(userRepository)
    }

    @Singleton
    @Provides
    fun provideFollowersUseCase(
        userRepository: UserRepository
    ): GetFollowersUseCase {
        return GetFollowersUseCaseImpl(userRepository)
    }

}