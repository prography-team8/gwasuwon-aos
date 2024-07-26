package com.prography.domain.lesson.model

/**
 * Created by MyeongKi.
 */
data class LessonSchedules(
    val id: Long,
    val schedules: List<LessonSchedule>,
    val hasStudent: Boolean,
    val rescheduleCount: Int,
    val studentName: String
)

data class LessonSchedule(
    val id: Long,
    val date: Long,
    val status: LessonScheduleStatus,
)