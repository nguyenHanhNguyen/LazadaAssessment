package com.lazada.network

import com.lazada.model.follower.FollowersApiEntity
import com.lazada.model.user.UserApiEntity
import retrofit2.Call
import retrofit2.Response

interface GithubService {

    fun getUser(userName: String): Call<UserApiEntity>

    fun getFollowers(userName: String): Call<List<FollowersApiEntity>>
}