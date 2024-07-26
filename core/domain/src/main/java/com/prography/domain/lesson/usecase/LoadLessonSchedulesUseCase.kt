package com.prography.domain.lesson.usecase

import com.prography.domain.lesson.model.LessonSchedules
import com.prography.domain.lesson.respository.LessonRepository
import kotlinx.coroutines.flow.Flow

/**
 * Created by MyeongKi.
 */
class LoadLessonSchedulesUseCase(private val repository: LessonRepository) {
    operator fun invoke(lessonId: Long): Flow<LessonSchedules> {
        return repository.loadLessonSchedules(lessonId)
    }
}
