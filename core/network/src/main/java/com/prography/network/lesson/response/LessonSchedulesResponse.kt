package com.prography.network.lesson.response

import kotlinx.serialization.Serializable

/**
 * Created by MyeongKi.
 */
@Serializable
data class LessonSchedulesResponse(
    val id: Long,
    val schedules: List<ScheduleResponse>,
    val hasStudent: Boolean,
    val rescheduleCount: Int,
    val studentName: String
)

@Serializable
data class ScheduleResponse(
    val id: Long,
    val date: Long,
    val status: String,
)