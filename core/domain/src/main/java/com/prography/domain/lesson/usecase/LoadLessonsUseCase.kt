package com.prography.domain.lesson.usecase

import androidx.paging.PagingData
import com.prography.domain.lesson.model.Lesson
import com.prography.domain.lesson.respository.LessonRepository
import kotlinx.coroutines.flow.Flow

/**
 * Created by MyeongKi.
 */
class LoadLessonsUseCase(private val repository: LessonRepository) {
    operator fun invoke(): Flow<PagingData<Lesson>> {
        return repository.loadLessons()
    }
}