package com.lazada.domain

import com.lazada.core.Result
import com.lazada.model.user.UserDomain

interface GetUserUseCase {
    suspend fun getUser(userName: String): Result<UserDomain>
}