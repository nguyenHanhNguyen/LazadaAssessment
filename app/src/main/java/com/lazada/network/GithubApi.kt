package com.lazada.network

import com.lazada.model.follower.FollowersApiEntity
import com.lazada.model.user.UserApiEntity
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {

    @GET("users/{user_name}")
    fun getUser(@Path("user_name") userName: String): Call<UserApiEntity>

    @GET("users/{user_name}/followers")
    fun getFollowers(@Path("user_name") userName: String): Call<List<FollowersApiEntity>>

}