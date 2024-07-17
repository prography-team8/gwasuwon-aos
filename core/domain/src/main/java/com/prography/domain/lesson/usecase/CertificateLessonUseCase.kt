package com.prography.domain.lesson.usecase

import com.prography.domain.lesson.model.Lesson
import com.prography.domain.lesson.respository.LessonRepository
import kotlinx.coroutines.flow.Flow

/**
 * Created by MyeongKi.
 */
class CertificateLessonUseCase(private val repository: LessonRepository) {
    operator fun invoke(lessonId: Long): Flow<Lesson> {
        return repository.certificateLesson(lessonId)
    }
}
