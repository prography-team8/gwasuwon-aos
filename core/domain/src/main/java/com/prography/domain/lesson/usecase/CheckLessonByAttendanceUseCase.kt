package com.prography.domain.lesson.usecase

import com.prography.domain.lesson.model.Lesson
import com.prography.domain.lesson.request.CheckLessonByAttendanceRequestOption
import com.prography.domain.lesson.respository.LessonRepository
import kotlinx.coroutines.flow.Flow

/**
 * Created by MyeongKi.
 */
class CheckLessonByAttendanceUseCase(private val repository: LessonRepository) {
    operator fun invoke(requestOption: CheckLessonByAttendanceRequestOption): Flow<Lesson> {
        return repository.checkLessonByAttendance(requestOption)
    }
}