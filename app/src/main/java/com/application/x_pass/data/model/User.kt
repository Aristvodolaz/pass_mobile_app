package com.application.x_pass.data.model

data class User(
    val username: String,
    val password: String,
    val userType: Int,
    val accessToken: String,
    val refreshToken: String
)
