package com.prography.domain.lesson

import com.prography.domain.lesson.model.Lesson
import com.prography.domain.lesson.model.LessonCard
import com.prography.domain.lesson.model.LessonSchedules
import com.prography.domain.lesson.request.CheckLessonByAttendanceRequestOption
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
    fun loadLessonInfoDetail(lessonId: Long): Flow<Lesson>
    fun updateLesson(requestOption: UpdateLessonRequestOption): Flow<Lesson>
    fun deleteLesson(lessonId: Long): Flow<Unit>
    fun checkLessonByAttendance(requestOption: CheckLessonByAttendanceRequestOption): Flow<LessonSchedules>
    fun joinLesson(lessonId: Long): Flow<Long>
    fun certificateLesson(lessonId: Long): Flow<Lesson>
}