package com.application.x_pass.data.remote.request_response


import com.google.gson.annotations.SerializedName

data class EventResponse(
    @SerializedName("Value") val value: EventsResponse.Value,
    @SerializedName("Success") val success: Boolean,
    @SerializedName("Message") val message: String,
    @SerializedName("ErrorCode") val errorCode: Int
)