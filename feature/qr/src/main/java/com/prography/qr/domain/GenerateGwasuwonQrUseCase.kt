package com.prography.qr.domain

import android.graphics.Bitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.prography.qr.domain.model.GwasuwonQr
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.json.Json

/**
 * Created by MyeongKi.
 */
class GenerateGwasuwonQrUseCase(
    private val generateQrUseCase: GenerateQrUseCase
) {
    operator fun invoke(
        qrData: GwasuwonQr
    ): Flow<Bitmap> = generateQrUseCase(Json.encodeToString(GwasuwonQr.serializer(), qrData))
}

class GenerateQrUseCase {
    operator fun invoke(
        data: String
    ): Flow<Bitmap> = flow {
        val bitMatrix = QRCodeWriter().encode(data, BarcodeFormat.QR_CODE, QR_SIZE, QR_SIZE)
        val bitmap = BarcodeEncoder().createBitmap(bitMatrix)
        emit(bitmap)
    }.flowOn(Dispatchers.IO)

    companion object {
        private const val QR_SIZE = 240
    }
}