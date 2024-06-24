package com.prography.lesson

import com.prography.domain.lesson.LessonDataSource
import com.prography.domain.lesson.model.Lesson
import com.prography.domain.lesson.model.LessonDay
import com.prography.domain.lesson.model.LessonDuration
import com.prography.domain.lesson.model.LessonSubject
import com.prography.domain.lesson.request.CreateLessonRequestOption
import com.prography.domain.lesson.request.LoadLessonsRequestOption
import com.prography.domain.lesson.request.UpdateLessonRequestOption
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by MyeongKi.
 */
class FakeLessonsDataSource : LessonDataSource {
    private val lessons = mutableListOf<Lesson>().apply {
//        add(Lesson(
//            lessonId = 0,
//            studentName = "studentName",
//            schoolYear = "schoolYear",
//            memo = "memo",
//            lessonSubject = LessonSubject.MATH,
//            lessonDay = listOf(LessonDay.FRIDAY),
//            lessonDuration = LessonDuration.ONE_HOUR,
//            lessonNumberOfProgress = 8,
//            lessonStartDate = "lessonStartDate",
//            restLesson = 1,
//            lessonContractUrl = "lessonContractUrl"
//
//        ))
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
                lessonStartDate = requestOption.lessonStartDate,
                restLesson = requestOption.lessonNumberOfProgress,
                lessonContractUrl = "test url"
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
                lessonStartDate = requestOption.lessonStartDate,
                restLesson = requestOption.lessonNumberOfProgress,
                lessonContractUrl = "test url"
            )
            lessons[lessons.indexOf(lesson)] = updatedLesson
            emit(updatedLesson)
        }
    }
}