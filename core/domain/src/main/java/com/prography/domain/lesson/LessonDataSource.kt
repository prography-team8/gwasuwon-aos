package com.prography.domain.lesson

import com.prography.domain.lesson.model.Lesson
import com.prography.domain.lesson.request.CreateLessonRequestOption
import com.prography.domain.lesson.request.LoadLessonsRequestOption
import kotlinx.coroutines.flow.Flow

/**
 * Created by MyeongKi.
 */
interface LessonDataSource {
    fun loadLessons(requestOption: LoadLessonsRequestOption): Flow<List<Lesson>>
    fun createLesson(requestOption: CreateLessonRequestOption): Flow<Lesson>
    fun loadLesson(lessonId: Long): Flow<Lesson>
    fun updateLesson(requestOption: CreateLessonRequestOption): Flow<Lesson>
}