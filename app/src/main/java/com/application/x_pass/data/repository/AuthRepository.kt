package com.application.x_pass.data.repository

import com.application.x_pass.data.remote.ApiService
import com.application.x_pass.data.remote.request_response.Auth2FARequest
import com.application.x_pass.data.remote.request_response.AuthRequest
import com.application.x_pass.data.remote.request_response.AuthResponse
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun login(username: String, password: String): AuthResponse {
        return try {
            apiService.loginUser(AuthRequest(username, password))
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun login2FA(username: String, password: String, code: String): AuthResponse {
        return try {
            apiService.loginUser2FA(Auth2FARequest(username, password, code))
        } catch (e: Exception) {
            throw e
        }
    }
}
