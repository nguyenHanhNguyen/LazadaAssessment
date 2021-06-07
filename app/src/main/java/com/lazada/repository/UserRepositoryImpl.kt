package com.lazada.repository

import com.lazada.core.Failure
import com.lazada.core.NetworkHandler
import com.lazada.core.Result
import com.lazada.model.follower.FollowersDomain
import com.lazada.model.user.UserDomain
import com.lazada.model.user.UserFailure
import com.lazada.network.GithubService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class UserRepositoryImpl constructor(
    private val apiService: GithubService,
    private val networkHandler: NetworkHandler,
    private val ioDispatcher: CoroutineDispatcher
) : UserRepository {

    override suspend fun getUserInfo(userName: String): Result<UserDomain> {
        return withContext(ioDispatcher) {
            if (networkHandler.isConnected()) {
                val response = apiService.getUser(userName).execute()
                when {
                    response.isSuccessful -> {
                        response.body()?.let {
                            Result.Success(it.toUserDomain())
                        } ?: Result.Success(UserDomain.empty())
                    }
                    else -> {
                        when (response.code()) {
                            404 -> Result.Error(UserFailure.UserNotFound())
                            else -> Result.Error(Failure.GeneralFailure)
                        }
                    }
                }
            } else {
                Result.Error(Failure.NetworkConnection)
            }
        }
    }

    override suspend fun getUserFollowers(userName: String): Result<List<FollowersDomain>> {
        return withContext(ioDispatcher) {
            if (networkHandler.isConnected()) {
                val response = apiService.getFollowers(userName).execute()
                when {
                    response.isSuccessful -> {
                        response.body()?.let { listFollowersApiEntity ->
                            val listFollowersDomain = mutableListOf<FollowersDomain>()
                            listFollowersApiEntity.forEach {
                                listFollowersDomain.add(it.toFollowersDomain())
                            }
                            Result.Success(listFollowersDomain)
                        } ?: Result.Success(emptyList())

                    }
                    else -> Result.Error(Failure.GeneralFailure)
                }
            } else {
                Result.Error(Failure.NetworkConnection)
            }
        }
    }

}