package com.lazada.repository

import com.lazada.core.DataHolder
import com.lazada.model.follower.FollowersDomain
import com.lazada.network.GithubService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepository @Inject constructor(private val apiService: GithubService) {

    suspend fun getUserInfo(userName: String): DataHolder? {
        return withContext(Dispatchers.IO) {
            val response = apiService.getUser(userName).execute()
            when {
                response.isSuccessful -> {
                    response.body()?.let {
                        DataHolder.Success(it.toUserDomain())
                    }
                }
                else -> DataHolder.Error("Something went wrong")
            }
        }
    }

    suspend fun getUserFollowers(userName: String): DataHolder? {
        return withContext(Dispatchers.IO) {
            val response = apiService.getFollowers(userName).execute()
            when {
                response.isSuccessful -> {
                    response.body()?.let { listFollowersApiEntity ->
                        val listFollowersDomain = mutableListOf<FollowersDomain>()
                        listFollowersApiEntity.forEach {
                            listFollowersDomain.add(it.toFollowersDomain())
                        }
                        DataHolder.Success(listFollowersDomain)
                    }
                }
                else -> DataHolder.Error("Something went wrong")
            }
        }
    }

}