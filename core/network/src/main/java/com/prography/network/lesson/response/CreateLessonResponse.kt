package com.prography.network.lesson.response

import kotlinx.serialization.Serializable

/**
 * Created by MyeongKi.
 */
@Serializable
data class CreateLessonResponse(
    val id: Long,
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