package com.application.x_pass.data.repository

import com.application.x_pass.data.remote.ApiService
import com.application.x_pass.data.remote.request_response.AuthResponse
import com.application.x_pass.data.remote.request_response.EventResponse
import com.application.x_pass.data.remote.request_response.EventsResponse
import com.application.x_pass.data.remote.request_response.InspectorEventResponse
import com.application.x_pass.data.remote.request_response.RefreshToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EventRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getEventInfo(id: Int): EventResponse = withContext(Dispatchers.IO) {
        apiService.getEvents(id)
    }

    suspend fun getEventsInfo(): EventsResponse = withContext(Dispatchers.IO) {
        apiService.getEvents()
    }

    suspend fun getInspectorForEvents(id: Int): InspectorEventResponse = withContext(Dispatchers.IO) {
        apiService.getInspectorForEvents(id)
    }

    suspend fun refreshToken(refresh: RefreshToken): AuthResponse = withContext(Dispatchers.IO) {
        apiService.refreshToken(refresh)
    }
}
