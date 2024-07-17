package com.prography.qr.domain

import android.graphics.Bitmap
import com.prography.domain.qr.model.GwasuwonQr
import com.prography.domain.qr.model.GwasuwonQrType
import com.prography.domain.qr.model.LessonCertificationData
import com.prography.utils.date.DateUtils
import kotlinx.coroutines.flow.Flow

/**
 * Created by MyeongKi.
 */
class GenerateLessonCertificationQrUseCase(
    private val generateGwasuwonQrUseCase: GenerateGwasuwonQrUseCase
) {
    operator fun invoke(
        lessonId: Long
    ): Flow<Bitmap> {
        val qrData = GwasuwonQr(
            type = GwasuwonQrType.LESSON_CERTIFICATION,
            data = LessonCertificationData(lessonId),
            createAt = DateUtils.getCurrentDateTime()
        )
        return generateGwasuwonQrUseCase(qrData)
    }
}