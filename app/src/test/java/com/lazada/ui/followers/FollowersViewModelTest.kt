package com.lazada.ui.followers

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lazada.MainCoroutineRule
import com.lazada.core.Failure
import com.lazada.core.Result
import com.lazada.domain.GetFollowersUseCase
import com.lazada.model.follower.FollowersDomain
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
class FollowersViewModelTest : TestCase() {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private val userName = "userName"

    private lateinit var followersViewModel: FollowersViewModel

    @Mock
    private lateinit var followersUseCase: GetFollowersUseCase

    @Before
    fun setup() {
        followersViewModel = FollowersViewModel(followersUseCase)
    }

    @Test
    fun `get followers success should update data`() {
        val followersDomain = FollowersDomain("login", 10, "avatarUrl")
        val listFollowers = listOf(followersDomain)
        `when`(runBlocking { followersUseCase.getFollowers(userName) }).thenReturn(
            Result.Success(
                listFollowers
            )
        )
        followersViewModel.followerList.observeForever {
            assertEquals(it[0].login, "login")
            assertEquals(it[0].id, 10)
            assertEquals(it[0].avatarUrl, "avatarUrl")
        }
        followersViewModel.getFollowers(userName)
    }

    @Test
    fun `get followers fail error network`() {
        val networkError = Result.Error(Failure.NetworkConnection)
        `when`(runBlocking { followersUseCase.getFollowers(userName) }).thenReturn(networkError)
        followersViewModel.networkError.observeForever {
            assertEquals(it, true)
        }
        followersViewModel.featureError.observeForever {
            assertEquals(it, false)
        }
        followersViewModel.generalError.observeForever {
            assertEquals(it, false)
        }
        followersViewModel.getFollowers(userName)
    }

    @Test
    fun `get followers fail error feature`() {
        val featureError = Result.Error(UserFailure.UserNotFound())
        `when`(runBlocking { followersUseCase.getFollowers(userName) }).thenReturn(featureError)
        followersViewModel.featureError.observeForever {
            assertEquals(it, true)
        }
        followersViewModel.networkError.observeForever {
            assertEquals(it, false)
        }
        followersViewModel.generalError.observeForever {
            assertEquals(it, false)
        }
        followersViewModel.getFollowers(userName)
    }

    @Test
    fun `get user fail error general`() {
        val generalError = Result.Error(Failure.GeneralFailure)
        `when`(runBlocking { followersUseCase.getFollowers(userName) }).thenReturn(generalError)
        followersViewModel.featureError.observeForever {
            assertEquals(it, false)
        }
        followersViewModel.networkError.observeForever {
            assertEquals(it, false)
        }
        followersViewModel.generalError.observeForever {
            assertEquals(it, true)
        }
        followersViewModel.getFollowers(userName)
    }

}