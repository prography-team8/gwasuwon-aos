package com.prography.domain.lesson

import com.prography.domain.lesson.model.Lesson
import com.prography.domain.lesson.request.CheckLessonByAttendanceRequestOption
import com.prography.domain.lesson.request.CreateLessonRequestOption
import com.prography.domain.lesson.request.LoadLessonsRequestOption
import com.prography.domain.lesson.request.UpdateLessonRequestOption
import kotlinx.coroutines.flow.Flow

/**
 * Created by MyeongKi.
 */
interface LessonDataSource {
    fun loadLessonContractUrl(lessonId: Long): Flow<String>
    fun loadLessons(requestOption: LoadLessonsRequestOption): Flow<List<Lesson>>
    fun createLesson(requestOption: CreateLessonRequestOption): Flow<Lesson>
    fun loadLesson(lessonId: Long): Flow<Lesson>
    fun updateLesson(requestOption: UpdateLessonRequestOption): Flow<Lesson>
    fun deleteLesson(lessonId: Long): Flow<Unit>
    fun checkLessonByAttendance(requestOption: CheckLessonByAttendanceRequestOption): Flow<Lesson>
}