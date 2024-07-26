package com.prography.domain.lesson.usecase

import com.prography.domain.lesson.respository.LessonRepository
import kotlinx.coroutines.flow.Flow

/**
 * Created by MyeongKi.
 */
class UpdateAttendanceLessonUseCase(private val repository: LessonRepository) {
    operator fun invoke(lessonId: Long): Flow<Long> {
        return repository.updateAttendanceLesson(lessonId)
    }
}
