package com.lazada.domain

import com.lazada.core.Result
import com.lazada.model.user.UserDomain
import com.lazada.repository.UserRepository
import javax.inject.Inject

class GetUserUseCaseImpl @Inject constructor(private val userRepository: UserRepository): GetUserUseCase {

    override suspend fun getUser(userName: String): Result<UserDomain> {
        return userRepository.getUserInfo(userName)
    }

}