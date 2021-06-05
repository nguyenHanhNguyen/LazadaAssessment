package com.lazada.model.user

data class UserView (
    var login: String,
    var avatarUrl: String,
    var name: String,
    var company: String,
    var blog: String,
    var location: String,
    var twitterUsername: String,
    var followers: Int
)