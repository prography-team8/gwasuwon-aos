package com.prography.domain.lesson.respository

import androidx.paging.PagingData
import com.prography.domain.lesson.model.Lesson
import com.prography.domain.lesson.request.CheckLessonByAttendanceRequestOption
import com.prography.domain.lesson.request.CreateLessonRequestOption
import com.prography.domain.lesson.request.UpdateLessonRequestOption
import kotlinx.coroutines.flow.Flow

/**
 * Created by MyeongKi.
 */
interface LessonRepository {
    fun loadLessonContractUrl(lessonId: Long): Flow<String>
    fun loadLessons(): Flow<PagingData<Lesson>>
    fun createLesson(requestOption: CreateLessonRequestOption): Flow<Lesson>
    fun loadLesson(lessonId: Long): Flow<Lesson>
    fun updateLesson(requestOption: UpdateLessonRequestOption): Flow<Lesson>
    fun deleteLesson(lessonId: Long): Flow<Unit>
    fun checkLessonByAttendance(requestOption: CheckLessonByAttendanceRequestOption): Flow<Lesson>
    fun participateLesson(lessonId: Long): Flow<Lesson>
}