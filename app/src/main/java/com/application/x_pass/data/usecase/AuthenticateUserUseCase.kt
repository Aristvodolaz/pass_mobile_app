package com.application.x_pass.data.usecase

import com.application.x_pass.data.remote.request_response.AuthResponse
import com.application.x_pass.data.repository.AuthRepository
import javax.inject.Inject

class AuthenticateUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend fun execute(username: String, password: String): AuthResponse {
        return authRepository.login(username, password)
    }

    suspend fun execute2FA(username: String, password: String, code: String): AuthResponse {
        return authRepository.login2FA(username, password, code)
    }
}
