package com.prography.domain.lesson.usecase

import androidx.paging.PagingData
import com.prography.domain.lesson.model.LessonCard
import com.prography.domain.lesson.respository.LessonRepository
import kotlinx.coroutines.flow.Flow

/**
 * Created by MyeongKi.
 */
class LoadLessonsUseCase(private val repository: LessonRepository) {
    operator fun invoke(): Flow<PagingData<LessonCard>> {
        return repository.loadLessonCards()
    }
}