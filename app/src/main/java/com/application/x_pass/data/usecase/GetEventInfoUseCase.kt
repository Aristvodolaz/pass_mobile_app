package com.application.x_pass.data.usecase

import com.application.x_pass.data.remote.request_response.EventsResponse
import com.application.x_pass.data.repository.EventRepository
import retrofit2.HttpException
import javax.inject.Inject

class GetEventInfoUseCase @Inject constructor(
    private val eventRepository: EventRepository,
    private val tokenRefreshUseCase: TokenRefreshUseCase
) {

    suspend operator fun invoke(): EventsResponse {
        return try {
            eventRepository.getEventsInfo()
        } catch (e: HttpException) {
            if (e.code() == 401) {
                tokenRefreshUseCase.refreshToken()
                eventRepository.getEventsInfo()
            } else {
                throw e
            }
        }
    }
}