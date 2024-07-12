package com.prography.lesson.fake

import com.prography.domain.lesson.LessonDataSource
import com.prography.domain.lesson.model.Lesson
import com.prography.domain.lesson.model.LessonDay
import com.prography.domain.lesson.model.LessonDuration
import com.prography.domain.lesson.model.LessonSubject
import com.prography.domain.lesson.request.CheckLessonByAttendanceRequestOption
import com.prography.domain.lesson.request.CreateLessonRequestOption
import com.prography.domain.lesson.request.LoadLessonsRequestOption
import com.prography.domain.lesson.request.UpdateLessonRequestOption
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

/**
 * Created by MyeongKi.
 */
class FakeLessonsDataSource : LessonDataSource {
    private val lessons = mutableListOf<Lesson>().apply {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        val date: Date = dateFormat.parse("2024-07-10 00:00:00")
        val attendance: Date = dateFormat.parse("2024-07-10 00:00:00")
        val attendance2: Date = dateFormat.parse("2024-07-17 00:00:00")

        val absent: Date = dateFormat.parse("2024-07-15 00:00:00")

        add(
            Lesson(
                lessonId = -1,
                studentName = "name",
                schoolYear = "schoolYear",
                memo = "memo",
                lessonSubject = LessonSubject.MATH,
                lessonDay = listOf(LessonDay.MONDAY, LessonDay.WEDNESDAY),
                lessonDuration = LessonDuration.ONE_HOUR,
                lessonNumberOfProgress = 8,
                lessonStartDateTime = date.time,
                lessonContractUrl = "test url",
                lessonNumberOfPostpone = 0,
                lessonAttendanceDates = listOf(attendance.time, attendance2.time),
                lessonAbsentDates = listOf(absent.time)
            )
        )
    }

    override fun loadLessonContractUrl(lessonId: Long): Flow<String> {
        return flow {
            emit("https://www.naver.com/")
        }
    }

    override fun loadLessons(requestOption: LoadLessonsRequestOption): Flow<List<Lesson>> {
        return flow {
            val start = (requestOption.page - 1) * requestOption.pageSize
            val end = start + requestOption.pageSize
            val result = lessons.subList(start, end.coerceAtMost(lessons.size))
            emit(result)
        }
    }

    override fun createLesson(requestOption: CreateLessonRequestOption): Flow<Lesson> {
        return flow {
            val lesson = Lesson(
                lessonId = lessons.size.toLong(),
                studentName = requestOption.studentName,
                schoolYear = requestOption.schoolYear,
                memo = requestOption.memo,
                lessonSubject = requestOption.lessonSubject,
                lessonDay = requestOption.lessonDay,
                lessonDuration = requestOption.lessonDuration,
                lessonNumberOfProgress = requestOption.lessonNumberOfProgress,
                lessonStartDateTime = requestOption.lessonStartDateTime,
                lessonContractUrl = "test url",
                lessonNumberOfPostpone = requestOption.lessonNumberOfPostpone,
                lessonAttendanceDates = emptyList(),
                lessonAbsentDates = emptyList()
            )
            lessons.add(lesson)
            emit(lesson)
        }
    }

    override fun loadLesson(lessonId: Long): Flow<Lesson> {
        return flow {
            val lesson = lessons.find { it.lessonId == lessonId }
            if (lesson == null) {
                throw IllegalArgumentException("lesson not found")
            }
            emit(lesson)
        }
    }

    override fun updateLesson(requestOption: UpdateLessonRequestOption): Flow<Lesson> {
        return flow {
            val lesson = lessons.find { it.lessonId == requestOption.lessonId }
            if (lesson == null) {
                throw IllegalArgumentException("lesson not found")
            }
            val updatedLesson = lesson.copy(
                studentName = requestOption.studentName,
                schoolYear = requestOption.schoolYear,
                memo = requestOption.memo,
                lessonSubject = requestOption.lessonSubject,
                lessonDay = requestOption.lessonDay,
                lessonDuration = requestOption.lessonDuration,
                lessonNumberOfProgress = requestOption.lessonNumberOfProgress,
                lessonStartDateTime = requestOption.lessonStartDateTime,
                lessonContractUrl = "test url"
            )
            lessons[lessons.indexOf(lesson)] = updatedLesson
            emit(updatedLesson)
        }
    }

    override fun deleteLesson(lessonId: Long): Flow<Unit> {
        return flow {
            val lesson = lessons.find { it.lessonId == lessonId }
            if (lesson == null) {
                throw IllegalArgumentException("lesson not found")
            }
            lessons.remove(lesson)
            emit(Unit)
        }
    }

    override fun checkLessonByAttendance(requestOption: CheckLessonByAttendanceRequestOption): Flow<Lesson> {
        return flow {
            val lesson = lessons.find { it.lessonId == requestOption.lessonId }
            if (lesson == null) {
                throw IllegalArgumentException("lesson not found")
            }
            val updatedLesson = lesson.copy(
                lessonAttendanceDates = lesson.lessonAttendanceDates + requestOption.lessonAbsentDate,
                lessonAbsentDates = lesson.lessonAbsentDates.filter { it != requestOption.lessonAbsentDate }
            )
            lessons[lessons.indexOf(lesson)] = updatedLesson
            emit(updatedLesson)
        }
    }
}