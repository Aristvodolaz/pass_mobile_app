package com.application.x_pass.viewModel.inspector

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.x_pass.data.remote.request_response.HistoryCheckResponse
import com.application.x_pass.data.usecase.InspectorHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InspectorHistoryViewModel @Inject constructor(
    private val getHistoryUseCase: InspectorHistoryUseCase
) : ViewModel() {

    val isLoading = mutableStateOf(false)
    val historyItems = mutableStateOf<List<HistoryCheckResponse.Item>>(emptyList())
    val errorMessage = mutableStateOf<String?>(null)

    fun fetchHistory() {
        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = null

            try {
                val response = getHistoryUseCase()
                historyItems.value = response.value.items
            } catch (e: Exception) {
                errorMessage.value = e.localizedMessage ?: "Unknown error occurred"
            } finally {
                isLoading.value = false
            }
        }
    }
}
