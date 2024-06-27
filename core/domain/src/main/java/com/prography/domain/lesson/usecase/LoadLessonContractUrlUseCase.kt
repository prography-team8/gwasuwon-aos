package com.prography.domain.lesson.usecase

import com.prography.domain.lesson.respository.LessonRepository
import kotlinx.coroutines.flow.Flow

/**
 * Created by MyeongKi.
 */
class LoadLessonContractUrlUseCase(private val repository: LessonRepository) {
    operator fun invoke(lessonId: Long): Flow<String> {
        return repository.loadLessonContractUrl(lessonId)
    }
}