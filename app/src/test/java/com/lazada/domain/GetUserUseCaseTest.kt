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
class GetUserUseCaseTest : TestCase() {

    private lateinit var getUserUseCaseImpl: GetUserUseCaseImpl

    @Mock
    lateinit var userRepository: UserRepository

    @Before
    fun setup() {
        getUserUseCaseImpl = GetUserUseCaseImpl((userRepository))
    }

    @Test
    fun `getUserUseCase should get data from repository`() {
        runBlocking { getUserUseCaseImpl.getUser("userName") }
        runBlocking { verify(userRepository, times(1)).getUserInfo("userName") }
        verifyNoMoreInteractions(userRepository)
    }

}