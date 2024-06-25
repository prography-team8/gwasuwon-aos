package com.prography.lesson.utils

import androidx.annotation.StringRes
import com.prography.configuration.R
import com.prography.domain.lesson.model.LessonDay
import com.prography.domain.lesson.model.LessonDuration
import com.prography.domain.lesson.model.LessonSubject

/**
 * Created by MyeongKi.
 */
@StringRes
fun LessonDay.getLessonDayStringRes(): Int {
    return when (this) {
        LessonDay.MONDAY -> R.string.lesson_day_monday
        LessonDay.TUESDAY -> R.string.lesson_day_tuesday
        LessonDay.WEDNESDAY -> R.string.lesson_day_wednesday
        LessonDay.THURSDAY -> R.string.lesson_day_thursday
        LessonDay.FRIDAY -> R.string.lesson_day_friday
        LessonDay.SATURDAY -> R.string.lesson_day_saturday
        LessonDay.SUNDAY -> R.string.lesson_day_sunday
    }
}

fun List<LessonDay>.getLessonDayStringRes(): List<Int> {
    return this
        .distinct()
        .sortedBy { it.index }
        .map { it.getLessonDayStringRes() }
}
@StringRes
fun LessonDuration.getLessonDurationStringRes(): Int {
    return when (this) {
        LessonDuration.ONE_HOUR -> R.string.lesson_duration_one_hour
        LessonDuration.ONE_AND_HALF_HOUR -> R.string.lesson_duration_one_and_half_hour
        LessonDuration.TWO_HOUR -> R.string.lesson_duration_two_hour
        LessonDuration.TWO_AND_HALF_HOUR -> R.string.lesson_duration_two_and_half_hour
        LessonDuration.THREE_HOUR -> R.string.lesson_duration_three_hour
        LessonDuration.THREE_HOUR_MORE -> R.string.lesson_duration_three_hour_more
    }
}

@StringRes
fun LessonSubject.getLessonSubjectStringRes(): Int {
    return when (this) {
        LessonSubject.MATH -> R.string.lesson_subject_math
        LessonSubject.ENGLISH -> R.string.lesson_subject_english
        LessonSubject.KOREAN -> R.string.lesson_subject_korean
        LessonSubject.SCIENCE -> R.string.lesson_subject_science
        LessonSubject.SOCIETY -> R.string.lesson_subject_society
        LessonSubject.OTHERS -> R.string.lesson_subject_others
    }
}