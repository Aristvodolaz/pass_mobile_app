package com.application.x_pass.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.x_pass.data.remote.request_response.CheckQrResponse
import com.application.x_pass.data.remote.request_response.TicketResponse
import com.application.x_pass.data.remote.request_response.VipTicketResponse
import com.application.x_pass.data.remote.request_response.VipTicketsRequest
import com.application.x_pass.data.repository.QRScannerRepository
import com.application.x_pass.data.usecase.ScannerUseCase
import com.application.x_pass.utils.SPHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

@HiltViewModel
class QrScannerViewModel(
    private val repository: ScannerUseCase,
    private val spHelper: SPHelper
) : ViewModel() {

    private val _scanResult = MutableStateFlow<String?>(null)
    val scanResult = _scanResult.asStateFlow()

    private val _isFlashEnabled = MutableStateFlow(false)
    val isFlashEnabled = _isFlashEnabled.asStateFlow()

    private val _isEntranceChecked = MutableStateFlow(true)
    val isEntranceChecked = _isEntranceChecked.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    private val _successMessage = MutableStateFlow<String?>(null)
    val successMessage = _successMessage.asStateFlow()

    private val _vipTicketRemaining = MutableStateFlow<Int?>(null)
    val vipTicketRemaining = _vipTicketRemaining.asStateFlow()

    private val _familyMembers = MutableStateFlow<List<CheckQrResponse.Value>?>(null)
    val familyMembers = _familyMembers.asStateFlow()

    fun setFlashEnabled(enabled: Boolean) {
        _isFlashEnabled.value = enabled
    }

    fun toggleEntrance() {
        _isEntranceChecked.value = !_isEntranceChecked.value
    }

    fun handleScanResult(qr: String, entrance: Boolean) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = repository.checkQr(
                    qr = qr,
                    entrance)
                processCheckQrResponse(response)
            } catch (e: HttpException) {
                handleHttpException(e)
            } catch (e: Exception) {
                handleException(e)
            } finally {
                _loading.value = false
            }
        }
    }

    fun handleScanResultWithoutEntrance(qr: String) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = repository.checkQrWithoutEntrance(qr)
                processCheckQrResponse(response)
            } catch (e: HttpException) {
                handleHttpException(e)
            } catch (e: Exception) {
                handleException(e)
            } finally {
                _loading.value = false
            }
        }
    }

    private fun handleHttpException(e: HttpException) {
            handleException(e)

    }

    private fun handleException(e: Throwable) {
        _errorMessage.value = e.localizedMessage ?: "An unexpected error occurred"
    }

    private fun processCheckQrResponse(response: CheckQrResponse) {
        when (response.errorCode) {
            609 -> {
                _familyMembers.value = response.value
                _successMessage.value = "Multiple participants detected. Please choose one."
            }
            603 -> _errorMessage.value = response.message
            613 -> {
                _vipTicketRemaining.value = response.extendedResponse?.remainingNumberOfAvailablePlaces
                _successMessage.value = "VIP Ticket selected. Remaining: ${_vipTicketRemaining.value}"
            }
            604 -> _errorMessage.value = "This QR code was already used."
            else -> {
                if (response.success) {
                    _successMessage.value = response.message
                } else {
                    _errorMessage.value = response.message
                }
            }
        }
    }

    fun handleFamilySelection(ids: List<Int>) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = repository.checkFamilyQr(
                    eventId = spHelper.getEventId(),
                    ids = ids,
                    isEntrance = _isEntranceChecked.value
                )
                processFamilyQrResponse(response)
            } catch (e: Exception) {
                handleException(e)
            } finally {
                _loading.value = false
            }
        }
    }

    fun checkVipTickets(request: VipTicketsRequest) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = repository.checkTicketVipQr(request)
                processVipTicketResponse(response)
            } catch (e: HttpException) {
                handleException(e)
            } catch (e: Exception) {
                handleException(e)
            } finally {
                _loading.value = false
            }
        }
    }

    private fun processFamilyQrResponse(response: TicketResponse) {
        if (response.success) {
            _successMessage.value = response.msg
        } else {
            _errorMessage.value = response.msg
        }
    }

    private fun processVipTicketResponse(response: VipTicketResponse) {
        if (response.success) {
            _successMessage.value = response.message
        } else {
            _errorMessage.value = response.message
        }
    }
}
