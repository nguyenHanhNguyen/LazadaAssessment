package com.lazada.repository

import com.lazada.core.DataHolder

interface UserRepository {
    suspend fun getUserInfo(userName: String): DataHolder?
    suspend fun getUserFollowers(userName: String): DataHolder?
}