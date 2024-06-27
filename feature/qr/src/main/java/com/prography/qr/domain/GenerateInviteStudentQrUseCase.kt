package com.prography.qr.domain

import android.graphics.Bitmap
import com.prography.qr.domain.model.GwasuwonQr
import com.prography.qr.domain.model.GwasuwonQrType
import com.prography.qr.domain.model.InviteStudentData
import com.prography.utils.date.DateUtils
import kotlinx.coroutines.flow.Flow

/**
 * Created by MyeongKi.
 */
class GenerateInviteStudentQrUseCase(
    private val generateQrUseCase: GenerateQrUseCase
) {
    operator fun invoke(
        lessonId: Long
    ): Flow<Bitmap> {
        val qrData = GwasuwonQr(
            type = GwasuwonQrType.INVITE_STUDENT,
            data = InviteStudentData(lessonId),
            createAt = DateUtils.getCurrentDateTime()
        )
        return generateQrUseCase(qrData)
    }
}