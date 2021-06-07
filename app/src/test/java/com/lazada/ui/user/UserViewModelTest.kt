package com.lazada.ui.user

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lazada.MainCoroutineRule
import com.lazada.core.Failure
import com.lazada.core.Result
import com.lazada.domain.GetUserUseCase
import com.lazada.model.user.UserDomain
import com.lazada.model.user.UserFailure
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UserViewModelTest : TestCase() {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private val userName = "userName"

    private lateinit var userViewModel: UserViewModel

    @Mock
    private lateinit var getUserUseCase: GetUserUseCase

    @Before
    fun setup() {
        userViewModel = UserViewModel(getUserUseCase)
    }

    @Test
    fun `get user success should update data`() {
        val user =
            UserDomain(
                "login",
                "avatarUrl",
                "name",
                "company",
                "blog",
                "location",
                "twitterUserName",
                10
            )
        `when`(runBlocking { getUserUseCase.getUser(userName) }).thenReturn(Result.Success(user))

        userViewModel.userProfile.observeForever {
            assertEquals("login", it.login)
            assertEquals("avatarUrl", it.avatarUrl)
            assertEquals("name", it.name)
            assertEquals("company", it.company)
            assertEquals("blog", it.blog)
            assertEquals("location", it.location)
            assertEquals("twitterUserName", it.twitterUsername)
            assertEquals(10, it.followers)
        }

        userViewModel.getUserInfo(userName)
    }

    @Test
    fun `get user fail error network`() {
        val networkError = Result.Error(Failure.NetworkConnection)
        `when`(runBlocking { getUserUseCase.getUser(userName) }).thenReturn(networkError)
        userViewModel.networkError.observeForever {
            assertEquals(it, true)
        }
        userViewModel.featureError.observeForever {
            assertEquals(it, false)
        }
        userViewModel.generalError.observeForever {
            assertEquals(it, false)
        }
        userViewModel.getUserInfo(userName)
    }

    @Test
    fun `get user fail error feature`() {
        val featureError = Result.Error(UserFailure.UserNotFound())
        `when`(runBlocking { getUserUseCase.getUser(userName) }).thenReturn(featureError)
        userViewModel.featureError.observeForever {
            assertEquals(true, it)
        }
        userViewModel.networkError.observeForever {
            assertEquals(it, false)
        }
        userViewModel.generalError.observeForever {
            assertEquals(it, false)
        }
        userViewModel.getUserInfo(userName)
    }

    @Test
    fun `get user fail error general`() {
        val generalError = Result.Error(Failure.GeneralFailure)
        `when`(runBlocking { getUserUseCase.getUser(userName) }).thenReturn(generalError)
        userViewModel.featureError.observeForever {
            assertEquals(it, false)
        }
        userViewModel.networkError.observeForever {
            assertEquals(it, false)
        }
        userViewModel.generalError.observeForever {
            assertEquals(it, true)
        }
        userViewModel.getUserInfo(userName)
    }

}