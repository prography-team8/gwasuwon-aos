package com.prography.domain.qr

/**
 * Created by MyeongKi.
 */
sealed interface CommonQrEvent {
    data class GetOnSuccessQr(val raw: String) : CommonQrEvent
    data class GetOnFailQr(val message: String) : CommonQrEvent
    data object RequestQrScan : CommonQrEvent
}