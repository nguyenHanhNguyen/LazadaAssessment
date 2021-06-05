package com.lazada.model.follower

data class FollowersDomain(var login: String, var id: Int, var avatarUrl: String) {
    fun toFollowersView() = FollowersView(
        login = login,
        id = id,
        avatarUrl = avatarUrl
    )
}