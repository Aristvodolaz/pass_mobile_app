package com.application.x_pass.data.remote.request_response

import com.google.gson.annotations.SerializedName

data class EventsResponse(
    @SerializedName("Value") val value: List<Value>,
    @SerializedName("Success") val success: Boolean,
    @SerializedName("Message") val message: String,
    @SerializedName("ErrorCode") val errorCode: Int
) {
    data class Value(
        @SerializedName("Id") val id: Int,
        @SerializedName("Title") val title: String,
        @SerializedName("Description") val description: String,
        @SerializedName("PosterUrl") val poster: String,
        @SerializedName("StartedAt") val startDate: String,
        @SerializedName("FinishedAt") val finishDate: String,
        @SerializedName("MaxParticipantsNumber") val maxParticipantsNumber: Int,
        @SerializedName("Status") val status: Int,
        @SerializedName("Direction") val direction: Int,
        @SerializedName("TimeShift") val timeShift: Float
    )
}
