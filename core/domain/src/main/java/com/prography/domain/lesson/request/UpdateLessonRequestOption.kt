package com.prography.domain.lesson.request

/**
 * Created by MyeongKi.
 */
data class UpdateLessonRequestOption(
    val lessonId: Long,
    val updateOption: CreateLessonRequestOption
)