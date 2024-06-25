package com.prography.domain.lesson.usecase

import com.prography.domain.lesson.model.Lesson
import com.prography.domain.lesson.request.CreateLessonRequestOption
import com.prography.domain.lesson.respository.LessonRepository
import kotlinx.coroutines.flow.Flow

/**
 * Created by MyeongKi.
 */
class CreateLessonUseCase(private val repository: LessonRepository) {
    operator fun invoke(requestOption: CreateLessonRequestOption): Flow<Lesson> {
        return repository.createLesson(requestOption)
    }
}