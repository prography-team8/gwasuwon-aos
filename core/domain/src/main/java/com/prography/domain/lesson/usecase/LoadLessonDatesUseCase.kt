package com.prography.domain.lesson.usecase

import com.prography.domain.lesson.model.Lesson
import com.prography.domain.lesson.model.LessonDay
import com.prography.utils.date.KTS
import com.prography.utils.date.toLocalDateTime
import com.prography.utils.date.toKrTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.math.min

/**
 * Created by MyeongKi.
 */
class LoadLessonDatesUseCase {
    operator fun invoke(lesson: Lesson): Flow<List<Long>> {
        return flow {
            val postponedLessonSize = min(lesson.lessonNumberOfPostpone, lesson.lessonAbsentDates.size)
            val startDateTime = lesson.lessonStartDateTime.toLocalDateTime()
            val lessonTimes = mutableListOf<Long>()
            var currentLessonCount = 0
            var currentDateTime: LocalDateTime = startDateTime
            while (currentLessonCount < (lesson.lessonNumberOfProgress + postponedLessonSize)) {
                if (lesson.lessonDay.contains(LessonDay.entries[currentDateTime.dayOfWeek.ordinal])) {
                    val lessonTime = currentDateTime.date.toKrTime()
                    lessonTimes.add(lessonTime)
                    currentLessonCount++
                }
                currentDateTime = currentDateTime.toInstant(KTS).plus(1, DateTimeUnit.DAY, KTS).toLocalDateTime(KTS)
            }
            emit(lessonTimes)
        }
    }
}