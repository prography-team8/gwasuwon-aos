package com.prography.network.lesson

import com.prography.domain.lesson.LessonDataSource
import com.prography.domain.lesson.model.Lesson
import com.prography.domain.lesson.model.LessonCard
import com.prography.domain.lesson.model.LessonDay
import com.prography.domain.lesson.model.LessonDuration
import com.prography.domain.lesson.model.LessonSchedule
import com.prography.domain.lesson.model.LessonScheduleStatus
import com.prography.domain.lesson.model.LessonSchedules
import com.prography.domain.lesson.model.LessonSubject
import com.prography.domain.lesson.request.CreateLessonRequestOption
import com.prography.domain.lesson.request.UpdateLessonRequestOption
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by MyeongKi.
 */

class LessonRemoteDataSource(
    private val httpClient: LessonHttpClient,
) : LessonDataSource {
    override fun loadLessonContractUrl(lessonId: Long): Flow<String> = flow {
        val result = httpClient.requestLessonContract(lessonId)
        emit(result.contractLink)
    }

    override fun loadLessonCards(): Flow<List<LessonCard>> = flow {
        val result = httpClient.requestLessonCards()
        emit(result.content.map {
            LessonCard(
                id = it.id,
                studentName = it.studentName,
                grade = it.grade,
                subject = LessonSubject.valueOf(it.subject),
                classDays = it.classDays.map { LessonDay.valueOf(it) },
                sessionDuration = LessonDuration.valueOf(it.sessionDuration),
                numberOfSessionsCompleted = it.numberOfSessionsCompleted,
                numberOfSessions = it.numberOfSessions
            )
        })
    }

    override fun createLesson(requestOption: CreateLessonRequestOption): Flow<Lesson> = flow {
        val result = httpClient.requestCreateLesson(requestOption)
        emit(
            Lesson(
                lessonId = result.id,
                studentName = result.studentName,
                grade = result.grade,
                memo = result.memo,
                subject = LessonSubject.valueOf(result.subject),
                classDays = result.classDays.map { LessonDay.valueOf(it) },
                sessionDuration = LessonDuration.valueOf(result.sessionDuration),
                numberOfSessions = result.numberOfSessions,
                rescheduleCount = result.rescheduleCount,
                startDate = result.startDate
            )
        )
    }

    override fun loadLessonSchedules(lessonId: Long): Flow<LessonSchedules> = flow {
        val result = httpClient.requestLessonDetail(lessonId)
        emit(
            LessonSchedules(
                id = result.id,
                schedules = result.schedules.map {
                    LessonSchedule(
                        id = it.id,
                        status = LessonScheduleStatus.valueOf(it.status),
                        date = it.date,
                    )
                },
                hasStudent = result.hasStudent,
                rescheduleCount = result.rescheduleCount,
                studentName = result.studentName,
            )
        )
    }

    override fun loadLesson(lessonId: Long): Flow<Lesson> = flow {
        val result = httpClient.requestLessonDetail(lessonId)
        emit(
            Lesson(
                lessonId = result.id,
                studentName = result.studentName,
                grade = result.grade,
                memo = result.memo,
                subject = LessonSubject.valueOf(result.subject),
                classDays = result.classDays.map { LessonDay.valueOf(it) },
                sessionDuration = LessonDuration.valueOf(result.sessionDuration),
                numberOfSessions = result.numberOfSessions,
                rescheduleCount = result.rescheduleCount,
                startDate = result.startDate
            )
        )
    }

    override fun updateLesson(requestOption: UpdateLessonRequestOption): Flow<Lesson> = flow {
        val result = httpClient.requestUpdateLesson(requestOption)
        emit(
            Lesson(
                lessonId = result.id,
                studentName = result.studentName,
                grade = result.grade,
                memo = result.memo,
                subject = LessonSubject.valueOf(result.subject),
                classDays = result.classDays.map { LessonDay.valueOf(it) },
                sessionDuration = LessonDuration.valueOf(result.sessionDuration),
                numberOfSessions = result.numberOfSessions,
                rescheduleCount = result.rescheduleCount,
                startDate = result.startDate
            )
        )
    }
    override fun deleteLesson(lessonId: Long): Flow<Unit> = flow {
        httpClient.requestDeleteLesson(lessonId)
        emit(Unit)
    }

    override fun updateForceAttendanceLesson(scheduleId: Long): Flow<Long>  = flow {
        httpClient.requestForceAttendanceLesson(scheduleId)
        emit(scheduleId)
    }

    override fun joinLesson(lessonId: Long): Flow<Long> = flow {
        val result = httpClient.requestJoinLesson(lessonId)
        emit(result.id)
    }

    override fun updateAttendanceLesson(lessonId: Long): Flow<Long> = flow {
        httpClient.requestAttendanceLesson(lessonId)
        emit(lessonId)
    }
}