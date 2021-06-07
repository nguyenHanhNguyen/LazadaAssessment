package com.lazada.repository

import com.lazada.core.Result
import com.lazada.model.follower.FollowersDomain
import com.lazada.model.user.UserDomain

interface UserRepository {
    suspend fun getUserInfo(userName: String): Result<UserDomain>
    suspend fun getUserFollowers(userName: String): Result<List<FollowersDomain>>
}