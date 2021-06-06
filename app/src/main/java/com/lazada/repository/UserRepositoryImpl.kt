package com.lazada.repository

import com.lazada.core.DataHolder
import com.lazada.core.Failure
import com.lazada.core.NetworkHandler
import com.lazada.model.follower.FollowersDomain
import com.lazada.model.user.UserFailure
import com.lazada.network.GithubService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: GithubService,
    private val networkHandler: NetworkHandler
) : UserRepository {

    override suspend fun getUserInfo(userName: String): DataHolder? {
        return withContext(Dispatchers.IO) {
            if (networkHandler.isConnected()) {
                val response = apiService.getUser(userName).execute()
                when {
                    response.isSuccessful -> {
                        response.body()?.let {
                            DataHolder.Success(it.toUserDomain())
                        }
                    }
                    else -> {
                        when (response.code()) {
                            404 -> DataHolder.Error(UserFailure.UserNotFound())
                            else -> DataHolder.Error(Failure.GeneralFailure)
                        }
                    }
                }
             } else {
                 DataHolder.Error(Failure.NetworkConnection)
            }
        }
    }

    override suspend fun getUserFollowers(userName: String): DataHolder? {
        return withContext(Dispatchers.IO) {
            if (networkHandler.isConnected()) {
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
                    else -> DataHolder.Error(Failure.GeneralFailure)
                }
            } else {
                DataHolder.Error(Failure.NetworkConnection)
            }
        }
    }

}