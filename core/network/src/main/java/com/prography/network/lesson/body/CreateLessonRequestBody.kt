package com.prography.network.lesson.body

import kotlinx.serialization.Serializable

/**
 * Created by MyeongKi.
 */
@Serializable
data class CreateLessonRequestBody(
    val studentName: String,
    val grade: String,
    val memo: String,
    val subject: String,
    val sessionDuration: String,
    val startDate: Long,
    val classDays: List<String>,
    val numberOfSessions: Int,
    val rescheduleCount: Int
)