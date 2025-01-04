package com.application.x_pass.data.usecase

import com.application.x_pass.data.remote.request_response.HistoryCheckResponse
import com.application.x_pass.data.repository.HistoryRepository
import com.application.x_pass.utils.SPHelper
import retrofit2.HttpException
import javax.inject.Inject

class InspectorHistoryUseCase@Inject constructor(
    private val historyRepository: HistoryRepository,
    private val tokenRefreshUseCase: TokenRefreshUseCase,
    private val spHelper: SPHelper
) {
    suspend operator fun invoke(): HistoryCheckResponse {
        return try {
            historyRepository.getInspectorHistory(spHelper.getEventId())
        } catch (e: HttpException) {
            if (e.code() == 401) {
                tokenRefreshUseCase.refreshToken()
                historyRepository.getInspectorHistory(spHelper.getEventId())
            } else {
                throw e
            }
        }
    }
}