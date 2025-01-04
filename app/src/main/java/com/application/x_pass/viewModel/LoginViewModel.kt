package com.application.x_pass.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.x_pass.data.remote.request_response.AuthResponse
import com.application.x_pass.data.usecase.AuthenticateUserUseCase
import com.application.x_pass.utils.SPHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import retrofit2.HttpException
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authenticateUserUseCase: AuthenticateUserUseCase,
    val spHelper: SPHelper
) : ViewModel() {

    private val _authResult = MutableLiveData<AuthResponse>()
    val authResult: LiveData<AuthResponse> = _authResult

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _login2FARequired = MutableLiveData<Boolean>()
    val login2FARequired: LiveData<Boolean> = _login2FARequired

    fun login(username: String, password: String) {
        viewModelScope.launch {
            try {
                val result = authenticateUserUseCase.execute(username, password)

                if (result.success) {
                    Log.d("refresh", result.value.refreshToken)
                    result.value.accessToken.let { spHelper.saveAccessToken(it) }
                    result.value.refreshToken.let { spHelper.saveRefreshToken(it)}
                    _authResult.value = result
                } else {
                    handleError(result.errorCode, result.message)
                }
            } catch (e: HttpException) {
                _errorMessage.value = e.message()
            } catch (e: Exception) {
                _errorMessage.value = e.localizedMessage ?: "An unexpected error occurred"
            }
        }
    }

    fun login2FA(username: String, password: String, code: String) {
        viewModelScope.launch {
            try {
                val result = authenticateUserUseCase.execute2FA(username, password, code)

                if (result.success) {
                    result.value.accessToken.let { spHelper.saveAccessToken(it) }
                    result.value.refreshToken.let { spHelper.saveRefreshToken(it)}
                    _authResult.value = result
                } else {
                    handleError(result.errorCode, result.message)
                }
            } catch (e: HttpException) {
                _errorMessage.value = e.message()
            } catch (e: Exception) {
                _errorMessage.value = e.localizedMessage ?: "An unexpected error occurred"
            }
        }
    }


    private fun handleError(errorCode: Int, message: String) {
        Log.d("LoginViewModel", "Error Code: $errorCode, Message: $message")
        when (errorCode) {
            401 -> _errorMessage.value = message
            614 -> _login2FARequired.value = true
            else -> _errorMessage.value = message ?: "Unknown error occurred"
        }
    }
}
