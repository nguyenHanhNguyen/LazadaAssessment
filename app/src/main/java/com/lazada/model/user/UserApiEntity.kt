package com.lazada.model.user

import com.google.gson.annotations.SerializedName

class UserApiEntity {
    @SerializedName("login")
    var login: String = ""

    @SerializedName("avatar_url")
    var avatarUrl: String = ""

    @SerializedName("name")
    var name: String? = null

    @SerializedName("company")
    var company: String? = null

    @SerializedName("blog")
    var blog: String? = null

    @SerializedName("location")
    var location: String? = null

    @SerializedName("twitter_username")
    var twitterUsername: String? = null

    @SerializedName("followers")
    var followers: Int = 0

    fun toUserDomain() = UserDomain(
        login = login,
        avatarUrl = avatarUrl,
        name = name ?: "",
        company = company ?: "",
        blog = blog ?: "",
        location = location ?: "",
        twitterUsername = twitterUsername ?: "",
        followers = followers
    )

}