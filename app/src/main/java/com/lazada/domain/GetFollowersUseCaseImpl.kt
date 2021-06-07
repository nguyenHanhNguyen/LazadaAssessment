package com.lazada.domain

import com.lazada.core.Result
import com.lazada.model.follower.FollowersDomain
import com.lazada.repository.UserRepository
import javax.inject.Inject

class GetFollowersUseCaseImpl @Inject constructor(private val userRepository: UserRepository): GetFollowersUseCase {

    override suspend fun getFollowers(userName: String): Result<List<FollowersDomain>> {
        return userRepository.getUserFollowers(userName)
    }

}