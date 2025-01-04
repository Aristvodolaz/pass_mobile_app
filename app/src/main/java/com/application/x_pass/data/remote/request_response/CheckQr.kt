package com.application.x_pass.data.remote.request_response

import com.google.gson.annotations.SerializedName

data class CheckQrResponse(
    @SerializedName("Success") val success: Boolean,
    @SerializedName("Message") val message: String?,
    @SerializedName("ErrorCode") val errorCode: Int,
    @SerializedName("Value") val value: List<Value>?,
    @SerializedName("ExtendedResponse") val extendedResponse: RemainingNumberOfAvailablePlaces?
) {
    data class Value(
        @SerializedName("Id") val id: Int,
        @SerializedName("UserName") val userName: String?,
        @SerializedName("UserType") val userType: String?,
        @SerializedName("isChecked") var isChecked: Boolean = false // Added default value for mutability
    )

    data class RemainingNumberOfAvailablePlaces(
        @SerializedName("RemainingNumberOfAvailablePlaces") val remainingNumberOfAvailablePlaces: Int
    )
}
