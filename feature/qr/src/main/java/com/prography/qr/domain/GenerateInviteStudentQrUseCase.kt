package com.prography.qr.domain

import android.graphics.Bitmap
import com.prography.domain.qr.model.GwasuwonQr
import com.prography.domain.qr.model.GwasuwonQrType
import com.prography.domain.qr.model.InviteStudentData
import com.prography.utils.date.DateUtils
import kotlinx.coroutines.flow.Flow

/**
 * Created by MyeongKi.
 */
class GenerateInviteStudentQrUseCase(
    private val generateGwasuwonQrUseCase: GenerateGwasuwonQrUseCase
) {
    operator fun invoke(
        lessonId: Long
    ): Flow<Bitmap> {
        val qrData = GwasuwonQr(
            type = GwasuwonQrType.INVITE_STUDENT,
            data = InviteStudentData(lessonId),
            createAt = DateUtils.getCurrentDateTime()
        )
        return generateGwasuwonQrUseCase(qrData)
    }
}