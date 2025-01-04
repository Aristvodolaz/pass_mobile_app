package com.application.x_pass.data.remote

import com.application.x_pass.data.remote.request_response.Auth2FARequest
import com.application.x_pass.data.remote.request_response.AuthRequest
import com.application.x_pass.data.remote.request_response.AuthResponse
import com.application.x_pass.data.remote.request_response.CheckQrResponse
import com.application.x_pass.data.remote.request_response.EventResponse
import com.application.x_pass.data.remote.request_response.EventsResponse
import com.application.x_pass.data.remote.request_response.HistoryCheckResponse
import com.application.x_pass.data.remote.request_response.InspectorEventResponse
import com.application.x_pass.data.remote.request_response.RefreshToken
import com.application.x_pass.data.remote.request_response.TicketResponse
import com.application.x_pass.data.remote.request_response.VipTicketResponse
import com.application.x_pass.data.remote.request_response.VipTicketsRequest
import com.application.x_pass.utils.Const
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query


interface ApiService {

    @POST(Const.AUTH)
    suspend fun loginUser(@Body authRequest: AuthRequest): AuthResponse

    @POST(Const.AUTH)
    suspend fun loginUser2FA(@Body authRequest: Auth2FARequest): AuthResponse

    @GET(Const.GET_EVENTS)
    suspend fun getEvents(): EventsResponse

    @GET(Const.GET_EVENT)
    suspend fun getEvents(@Query("id") eventId: Int): EventResponse

    @GET(Const.GET_EVENT_INSPECTOR)
    suspend fun getInspectorForEvents(@Query("eventId") id: Int): InspectorEventResponse

    @POST(Const.REFRESH_TOKEN)
    suspend fun refreshToken(@Body refreshTokenRequest: RefreshToken): AuthResponse

    @GET(Const.SCAN_HISTORY)
    suspend fun getHistoryCheck(@Query("eventId") eventId: Int, @Query("ItemsPerPage") num: Int): HistoryCheckResponse

    @GET(Const.SCAN_QR)
    suspend fun checkQr(@Query("code") code: String?, @Query("eventId") id: Int, @Query("isEntrance") isEntrance: Boolean): CheckQrResponse

    @GET(Const.SCAN_QR)
    suspend fun checkQrWithoutEntrance(@Query("code") code: String?, @Query("eventId") id: Int): CheckQrResponse

    @POST(Const.TIKETS)
    suspend fun checkTickets(@Query("eventId") eventId: Int, @Query("Tickets") array: List<Int?>?, @Query("isEntrance") isEntrance: Boolean): TicketResponse

    @POST(Const.TIKETS)
    suspend fun checkTicketsWithoutEntrance(@Query("eventId") eventId: Int, @Query("Tickets") array: List<Int?>?): TicketResponse

    @PATCH(Const.TIKETS_VIP)
    suspend fun checkVipTickets(@Body request: VipTicketsRequest): VipTicketResponse
}
