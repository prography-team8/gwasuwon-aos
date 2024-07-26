package com.prography.domain.lesson.model

/**
 * Created by MyeongKi.
 */
data class Lesson(
    val lessonId: Long,
    val studentName: String,
    val grade: String,
    val memo: String,
    val subject: LessonSubject,
    val classDays: List<LessonDay>,
    val sessionDuration: LessonDuration,
    val numberOfSessions: Int,
    val rescheduleCount: Int,
    val startDate: Long,
    val available: Boolean
)