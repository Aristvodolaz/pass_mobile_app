package com.application.x_pass.data.remote.request_response
import com.google.gson.annotations.SerializedName

data class InspectorEventResponse(
    @SerializedName("Value") val value: List<Value>,
    @SerializedName("Success") val success: Boolean,
    @SerializedName("Message") val message: String,
    @SerializedName("ErrorCode") val errorCode: Int
) {
    data class Value(
        @SerializedName("Id") val id: Long,
        @SerializedName("Username") val username: String,
        @SerializedName("FirstName") val firstName: String,
        @SerializedName("LastName") val lastName: String,
        @SerializedName("Password") val password: String
    )
}