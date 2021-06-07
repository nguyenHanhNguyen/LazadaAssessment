package com.lazada.model.user

data class UserDomain(
    var login: String,
    var avatarUrl: String,
    var name: String,
    var company: String,
    var blog: String,
    var location: String,
    var twitterUsername: String,
    var followers: Int
) {

    companion object {
        fun empty() = UserDomain(
            login = "",
            avatarUrl = "",
            name = "",
            company = "",
            blog = "",
            location = "",
            twitterUsername = "",
            followers = 0
        )
    }

    fun toUserView() = UserView(
        login = login,
        avatarUrl = avatarUrl,
        name = name,
        company = company,
        blog = blog,
        location = location,
        twitterUsername = twitterUsername,
        followers = followers
    )
}