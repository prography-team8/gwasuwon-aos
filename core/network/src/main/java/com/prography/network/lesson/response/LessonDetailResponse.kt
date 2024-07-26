package com.prography.network.lesson.response

import kotlinx.serialization.Serializable

/**
 * Created by MyeongKi.
 */
@Serializable
data class LessonDetailResponse(
    val id: Long,
    val studentName: String,
    val grade: String,
    val memo: String,
    val subject: String,
    val sessionDuration: String,
    val classDays:List<String>,
    val numberOfSessions: Int,
    val startDate: Long,
    val rescheduleCount: Int,
    val hasStudent: Boolean,
    val schedules: List<ScheduleResponse>,
)

@Serializable
data class ScheduleResponse(
    val id: Long,
    val date: Long,
    val status: String,
)