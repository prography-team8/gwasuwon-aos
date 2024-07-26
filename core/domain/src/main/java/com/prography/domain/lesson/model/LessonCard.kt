package com.prography.domain.lesson.model

/**
 * Created by MyeongKi.
 */
data class LessonCard(
    val id: Long,
    val studentName: String,
    val grade: String,
    val subject: LessonSubject,
    val classDays: List<LessonDay>,
    val sessionDuration: LessonDuration,
    val numberOfSessionsCompleted: Int,
    val numberOfSessions: Int
)