package com.lazada.network

import com.lazada.model.follower.FollowersApiEntity
import com.lazada.model.user.UserApiEntity
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

class GithubServiceImpl @Inject constructor(private val api: GithubApi) : GithubService {

    override fun getUser(userName: String): Call<UserApiEntity> {
        return api.getUser(userName)
    }

    override fun getFollowers(userName: String): Call<List<FollowersApiEntity>> {
        return api.getFollowers(userName)
    }
}