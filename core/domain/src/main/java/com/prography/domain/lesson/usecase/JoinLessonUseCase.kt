package com.prography.domain.lesson.usecase

import com.prography.domain.lesson.respository.LessonRepository
import kotlinx.coroutines.flow.Flow

class JoinLessonUseCase(private val repository: LessonRepository) {
    operator fun invoke(lessonId: Long): Flow<Long> {
        return repository.joinLesson(lessonId)
    }
}