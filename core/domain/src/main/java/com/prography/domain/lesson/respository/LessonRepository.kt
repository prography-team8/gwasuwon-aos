package com.prography.domain.lesson.respository

import androidx.paging.PagingData
import com.prography.domain.lesson.model.Lesson
import com.prography.domain.lesson.model.LessonCard
import com.prography.domain.lesson.model.LessonSchedules
import com.prography.domain.lesson.request.CreateLessonRequestOption
import com.prography.domain.lesson.request.UpdateLessonRequestOption
import kotlinx.coroutines.flow.Flow

/**
 * Created by MyeongKi.
 */
interface LessonRepository {
    fun loadLessonContractUrl(lessonId: Long): Flow<String>
    fun loadLessonCards(): Flow<PagingData<LessonCard>>
    fun createLesson(requestOption: CreateLessonRequestOption): Flow<Lesson>
    fun loadLessonSchedules(lessonId: Long): Flow<LessonSchedules>
    fun loadLesson(lessonId: Long): Flow<Lesson>
    fun updateLesson(requestOption: UpdateLessonRequestOption): Flow<Lesson>
    fun deleteLesson(lessonId: Long): Flow<Unit>
    fun joinLesson(lessonId: Long): Flow<Long>
    fun updateForceAttendanceLesson(scheduleId: Long): Flow<Long>
    fun updateAttendanceLesson(lessonId: Long): Flow<Long>
}