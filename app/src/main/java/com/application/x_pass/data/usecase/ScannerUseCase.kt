package com.application.x_pass.data.usecase

import com.application.x_pass.data.remote.request_response.CheckQrResponse
import com.application.x_pass.data.remote.request_response.TicketResponse
import com.application.x_pass.data.remote.request_response.VipTicketResponse
import com.application.x_pass.data.remote.request_response.VipTicketsRequest
import com.application.x_pass.data.repository.QRScannerRepository
import com.application.x_pass.utils.SPHelper
import retrofit2.HttpException
import javax.inject.Inject

class ScannerUseCase @Inject constructor(
    private val scannerRepository: QRScannerRepository,
    private val tokenRefreshUseCase: TokenRefreshUseCase,
    private val spHelper: SPHelper
) {

    suspend fun checkQr(qr: String, entrance: Boolean): CheckQrResponse {
        return try {
            scannerRepository.checkQr(qr, spHelper.getEventId(), entrance)
        } catch (e: HttpException) {
            if (e.code() == 401) {
                tokenRefreshUseCase.refreshToken()
                scannerRepository.checkQr(qr, spHelper.getEventId(), entrance)
            } else {
                throw e
            }
        }
    }

    suspend fun checkQrWithoutEntrance(qr: String): CheckQrResponse {
        return try {
            scannerRepository.checkQrWithoutEntrance(qr, spHelper.getEventId())
        } catch (e: HttpException) {
            if (e.code() == 401) {
                tokenRefreshUseCase.refreshToken()
                scannerRepository.checkQrWithoutEntrance(qr, spHelper.getEventId())
            } else {
                throw e
            }
        }
    }

    suspend fun checkFamilyQr(eventId: Int, ids: List<Int>, isEntrance: Boolean): TicketResponse {
        return try {
            scannerRepository.checkFamilyQr(eventId, ids, isEntrance)
        } catch (e: HttpException) {
            if (e.code() == 401) {
                tokenRefreshUseCase.refreshToken()
                scannerRepository.checkFamilyQr(eventId, ids, isEntrance)
            } else {
                throw e
            }
        }
    }

    suspend fun checkFamilyQrWithoutEntrance(eventId: Int, ids: List<Int>): TicketResponse {
        return try {
            scannerRepository.checkFamilyQrWithoutEntrance(eventId, ids)
        } catch (e: HttpException) {
            if (e.code() == 401) {
                tokenRefreshUseCase.refreshToken()
                scannerRepository.checkFamilyQrWithoutEntrance(eventId, ids)
            } else {
                throw e
            }
        }
    }

    suspend fun checkTicketVipQr(request: VipTicketsRequest): VipTicketResponse {
        return try {
            scannerRepository.checkQrVipTickets(request)
        } catch (e: HttpException) {
            if (e.code() == 401) {
                tokenRefreshUseCase.refreshToken()
                scannerRepository.checkQrVipTickets(request)
            } else {
                throw e
            }
        }
    }
}
