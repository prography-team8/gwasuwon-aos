package com.prography.qr.domain

import android.graphics.Bitmap
import com.prography.domain.lesson.usecase.LoadLessonContractUrlUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map

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
    ): Flow<Pair<Bitmap, String>> {
        return loadContractUrl(lessonId).flatMapConcat { url ->
            generateQrUseCase(url)
                .map { bitmap -> Pair(bitmap, url) }
        }
    }
}