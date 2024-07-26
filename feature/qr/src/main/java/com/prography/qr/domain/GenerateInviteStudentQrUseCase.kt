package com.prography.qr.domain

import android.graphics.Bitmap
import com.prography.domain.qr.model.GwasuwonQr
import com.prography.domain.qr.model.GwasuwonQrType
import com.prography.domain.qr.model.JoinClassData
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
            data = JoinClassData(classId = lessonId, type = GwasuwonQrType.JOIN_CLASS),
            createAt = DateUtils.getCurrentDateTime()
        )
        return generateGwasuwonQrUseCase(qrData)
    }
}