package com.lazada.domain

import com.lazada.core.DataHolder
import com.lazada.repository.UserRepositoryImpl
import javax.inject.Inject

class GetUserFollowersUserCase @Inject constructor(private val userRepository: UserRepositoryImpl) {

    suspend operator fun invoke(userName: String): DataHolder? {
        return userRepository.getUserFollowers(userName)
    }

}