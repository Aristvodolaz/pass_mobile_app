package com.application.x_pass.data.repository

import com.application.x_pass.data.remote.ApiService
import com.application.x_pass.data.remote.request_response.VipTicketsRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class QRScannerRepository(private val apiService: ApiService) {

    suspend fun checkQr(qr: String, eventId: Int, isEntrance: Boolean) =
        withContext(Dispatchers.IO) {
            apiService.checkQr(qr, eventId, isEntrance)
        }

    suspend fun checkQrWithoutEntrance(qr: String, eventId: Int) =
        withContext(Dispatchers.IO) {
            apiService.checkQrWithoutEntrance(qr, eventId)
        }

    suspend fun checkFamilyQr(eventId: Int, ids: List<Int>, isEntrance: Boolean) =
        withContext(Dispatchers.IO) {
            apiService.checkTickets(eventId, ids, isEntrance)
        }

    suspend fun checkFamilyQrWithoutEntrance(eventId: Int, ids: List<Int>) =
        withContext(Dispatchers.IO) {
            apiService.checkTicketsWithoutEntrance(eventId, ids)
        }

    suspend fun checkQrVipTickets(request: VipTicketsRequest) =
        withContext(Dispatchers.IO){
            apiService.checkVipTickets(request)
        }
}
