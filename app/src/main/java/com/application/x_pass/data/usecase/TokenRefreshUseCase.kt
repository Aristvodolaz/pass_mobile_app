package com.application.x_pass.data.usecase

import com.application.x_pass.data.remote.request_response.RefreshToken
import com.application.x_pass.data.repository.EventRepository
import com.application.x_pass.utils.SPHelper
import javax.inject.Inject

class TokenRefreshUseCase @Inject constructor(
    private val eventRepository: EventRepository,
    private val spHelper: SPHelper
) {

    suspend fun refreshToken() {
        val refreshToken = spHelper.getRefreshToken() ?: throw Exception("No refresh token found")
        val accessToken = spHelper.getAccessToken() ?: throw Exception("No access token found")

        val response = eventRepository.refreshToken(
            RefreshToken(accessToken = accessToken, refreshToken = refreshToken)
        )

        if (response.success) {
            spHelper.saveAccessToken(response.value.accessToken)
            spHelper.saveRefreshToken(response.value.refreshToken)
        } else {
            spHelper.clearSession()
            throw Exception("Session expired. Please log in again.")
        }
    }
}
