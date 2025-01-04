package com.application.x_pass.data.remote.request_response

import com.google.gson.annotations.SerializedName

data class AuthRequest(
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String
)

data class Auth2FARequest(
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String,
    @SerializedName("oneTimePassword") val oneTimePass: String
)

data class AuthResponse(
    @SerializedName("Value") val value: Value,
    @SerializedName("Success") val success: Boolean,
    @SerializedName("Message") val message: String,
    @SerializedName("ErrorCode") val errorCode: Int
) {
    data class Value(
        @SerializedName("Username") val username: String,
        @SerializedName("UserType") val userType: Int,
        @SerializedName("AccessToken") val accessToken: String,
        @SerializedName("RefreshToken") val refreshToken: String
    )
}


data class RefreshToken(
    @SerializedName("accessToken") val accessToken: String,
    @SerializedName("refreshToken") val refreshToken: String
)
