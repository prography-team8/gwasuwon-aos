package com.prography.domain.lesson.request

/**
 * Created by MyeongKi.
 */
data class CheckLessonByAttendanceRequestOption(
    val lessonId: Long,
    val lessonAbsentDate: Long
)