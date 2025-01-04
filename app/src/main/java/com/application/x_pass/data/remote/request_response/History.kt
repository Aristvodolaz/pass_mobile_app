package com.application.x_pass.data.remote.request_response

import com.google.gson.annotations.SerializedName

data class HistoryCheckResponse(
    @SerializedName("TotalCount") val totalCount: Int,
    @SerializedName("Value") val value: Value,
    @SerializedName("Success") val success: Boolean,
    @SerializedName("Message") val message: String?,
    @SerializedName("ErrorCode") val errorCode: Int
) {
    data class Value(
        @SerializedName("PageNumber") val pageNumber: Int,
        @SerializedName("TotalPages") val totalPages: Int,
        @SerializedName("TotalCount") val totalCount: Int,
        @SerializedName("Items") val items: List<Item>
    )

    data class Item(
        @SerializedName("CheckedAt") val checkedAt: String,
        @SerializedName("WhoChecked") val whoChecked: String,
        @SerializedName("ParticipantName") val participantName: String,
        @SerializedName("ParticipantType") val participantType: Int, // 0 - employee, 1 - spouse, 2 - child
        @SerializedName("ParticipantTypeName") val participantTypeName: String,
        @SerializedName("AccessStatus") val accessStatus: Int, // 0 - allowed, 1 - repeated, 2 - denied
        @SerializedName("AccessStatusName") val accessStatusName: String,
        @SerializedName("IsEntrance") val isEntrance: Boolean,
        @SerializedName("TimeShift") val timeShift: Float
    )
}
