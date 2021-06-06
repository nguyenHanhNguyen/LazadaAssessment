package com.lazada.domain

import com.lazada.core.DataHolder
import com.lazada.repository.UserRepository
import javax.inject.Inject

class GetUserFollowersUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend operator fun invoke(userName: String): DataHolder? {
        return userRepository.getUserFollowers(userName)
    }

}