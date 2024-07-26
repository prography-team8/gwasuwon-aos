package com.prography.domain.lesson.request

import com.prography.domain.lesson.model.LessonDay
import com.prography.domain.lesson.model.LessonDuration
import com.prography.domain.lesson.model.LessonSubject

/**
 * Created by MyeongKi.
 */
data class CreateLessonRequestOption(
    val studentName: String,
    val grade: String,
    val memo: String,
    val subject: LessonSubject,
    val lessonDays: List<LessonDay>,
    val sessionDuration: LessonDuration,
    val numberOfSessions: Int,
    val rescheduleCount: Int,
    val startDate: Long,
)