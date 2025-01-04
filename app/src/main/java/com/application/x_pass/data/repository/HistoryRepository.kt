package com.application.x_pass.data.repository

import com.application.x_pass.data.remote.ApiService
import com.application.x_pass.data.remote.request_response.HistoryCheckResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HistoryRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getInspectorHistory(id: Int): HistoryCheckResponse =
        withContext(Dispatchers.IO) {
            apiService.getHistoryCheck(id, 5000)
        }
}
