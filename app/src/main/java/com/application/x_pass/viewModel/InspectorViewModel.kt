package com.application.x_pass.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.x_pass.data.remote.request_response.EventsResponse
import com.application.x_pass.data.usecase.GetEventInfoUseCase
import com.application.x_pass.utils.SPHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class InspectorViewModel @Inject constructor(
    private val getEventInfoUseCase: GetEventInfoUseCase,
    private val spHelper: SPHelper
) : ViewModel() {

    var currentScreen = "main_inspector"
    val currentScreenState: String = currentScreen

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _eventData = mutableStateOf<EventsResponse?>(null)
    val eventData: State<EventsResponse?> = _eventData

    private val _errorMessage = mutableStateOf("")
    val errorMessage: State<String> = _errorMessage

    fun getEventInfo() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = ""
            try {
                _eventData.value = getEventInfoUseCase()
            } catch (e: Exception) {
                _errorMessage.value = "Error: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }
    fun clearSession() {
        spHelper.clearSession()
    }
}
