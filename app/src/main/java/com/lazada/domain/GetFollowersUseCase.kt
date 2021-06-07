package com.lazada.domain

import com.lazada.core.Result
import com.lazada.model.follower.FollowersDomain

interface GetFollowersUseCase {
    suspend fun getFollowers(userName: String): Result<List<FollowersDomain>>
}