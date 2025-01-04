package com.application.x_pass.data.remote.request_response

import com.google.gson.annotations.SerializedName

data class TicketResponse(
    @SerializedName("Success") val success: Boolean,
    @SerializedName("Message") val msg: String?,
    @SerializedName("ErrorCode") val code: Int,
    @SerializedName("Value") val value: List<Value>?
) {
    data class Value(
        @SerializedName("FirstName") val firstName: String?,
        @SerializedName("LastName") val lastName: String?
    )
}
