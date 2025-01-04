package com.application.x_pass.data.remote.request_response

import com.google.gson.annotations.SerializedName

data class VipTicketsRequest(
    @SerializedName("ticketId") val ticketId: Int,
    @SerializedName("numberOfEntries") val numberOfEntries: Int
)

data class VipTicketResponse(
    @SerializedName("Value") val value: Int,
    @SerializedName("Success") val success: Boolean,
    @SerializedName("Message") val message: String,
    @SerializedName("ErrorCode") val errorCode: Int
)
