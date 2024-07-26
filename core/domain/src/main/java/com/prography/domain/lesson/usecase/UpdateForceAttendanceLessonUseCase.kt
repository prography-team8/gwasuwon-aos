package com.prography.domain.lesson.usecase

import com.prography.domain.lesson.respository.LessonRepository
import kotlinx.coroutines.flow.Flow

/**
 * Created by MyeongKi.
 */
class UpdateForceAttendanceLessonUseCase(private val repository: LessonRepository) {
    operator fun invoke(scheduleId: Long): Flow<Long> {
        return repository.updateForceAttendanceLesson(scheduleId)
    }
}