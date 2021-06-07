package com.lazada.domain

import com.lazada.repository.UserRepository
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetUserFollowersUseCaseTest : TestCase() {

    private lateinit var getFollowersUseCaseImpl: GetFollowersUseCaseImpl

    @Mock
    private lateinit var userRepository: UserRepository

    @Before
    fun setup() {
        getFollowersUseCaseImpl = GetFollowersUseCaseImpl(userRepository)
    }

    @Test
    fun `getUserFollowersUseCase should get data from useRepository`() {
        val userName = "userName"
        runBlocking { getFollowersUseCaseImpl.getFollowers(userName) }
        runBlocking { verify(userRepository, times(1)).getUserFollowers(userName) }
        verifyNoMoreInteractions(userRepository)
    }
}