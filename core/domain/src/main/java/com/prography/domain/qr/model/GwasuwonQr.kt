package com.prography.domain.qr.model

import kotlinx.serialization.Serializable

/**
 * Created by MyeongKi.
 */

@Serializable
data class GwasuwonQr(
    val version: Int = GWASUWON_QR_VERSION,
    val type: GwasuwonQrType,
    @Serializable(with = GwasuwonQrDataSerializer::class)
    val data: GwasuwonQrData,
    val createAt: Long,
)
