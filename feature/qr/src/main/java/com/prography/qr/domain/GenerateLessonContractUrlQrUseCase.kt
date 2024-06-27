package com.prography.qr.domain

import android.graphics.Bitmap
import com.prography.domain.lesson.usecase.LoadLessonContractUrlUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat

/**
 * Created by MyeongKi.
 */
class GenerateLessonContractUrlQrUseCase(
    private val generateQrUseCase: GenerateQrUseCase,
    private val loadContractUrl: LoadLessonContractUrlUseCase
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(
        lessonId: Long
    ): Flow<Bitmap> {
        return loadContractUrl(lessonId).flatMapConcat { url ->
            generateQrUseCase(url)
        }
    }
}