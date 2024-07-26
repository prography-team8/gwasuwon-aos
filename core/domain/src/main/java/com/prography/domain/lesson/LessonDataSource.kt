package com.prography.domain.lesson

import com.prography.domain.lesson.model.Lesson
import com.prography.domain.lesson.model.LessonCard
import com.prography.domain.lesson.model.LessonSchedules
import com.prography.domain.lesson.request.CreateLessonRequestOption
import com.prography.domain.lesson.request.UpdateLessonRequestOption
import kotlinx.coroutines.flow.Flow

/**
 * Created by MyeongKi.
 */
interface LessonDataSource {
    fun loadLessonContractUrl(lessonId: Long): Flow<String>
    fun loadLessonCards(): Flow<List<LessonCard>>
    fun createLesson(requestOption: CreateLessonRequestOption): Flow<Lesson>
    fun loadLessonSchedules(lessonId: Long): Flow<LessonSchedules>
    fun loadLesson(lessonId: Long): Flow<Lesson>
    fun updateLesson(requestOption: UpdateLessonRequestOption): Flow<Lesson>
    fun deleteLesson(lessonId: Long): Flow<Unit>
    fun updateForceAttendanceLesson(scheduleId: Long): Flow<Long>
    fun joinLesson(lessonId: Long): Flow<Long>
    fun updateAttendanceLesson(lessonId: Long): Flow<Long>
}