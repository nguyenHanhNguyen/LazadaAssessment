package com.lazada.model.follower

import com.google.gson.annotations.SerializedName

class FollowersApiEntity {

    @SerializedName("login")
    var login: String = ""

    @SerializedName("id")
    var id: Int = 0

    @SerializedName("avatar_url")
    var avatarUrl: String = ""

    fun toFollowersDomain() = FollowersDomain(
        login = login,
        id = id,
        avatarUrl = avatarUrl
    )
}