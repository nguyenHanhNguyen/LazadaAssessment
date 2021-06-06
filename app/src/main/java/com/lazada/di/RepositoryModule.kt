package com.lazada.di

import com.lazada.core.NetworkHandler
import com.lazada.network.GithubService
import com.lazada.repository.UserRepository
import com.lazada.repository.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideUserRepository(
        apiService: GithubService,
        networkHandler: NetworkHandler
    ): UserRepository {
        return UserRepositoryImpl(apiService, networkHandler)
    }

}