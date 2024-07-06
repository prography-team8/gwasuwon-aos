package com.prography.domain.lesson.request

import com.prography.domain.lesson.model.LessonDay
import com.prography.domain.lesson.model.LessonDuration
import com.prography.domain.lesson.model.LessonSubject

/**
 * Created by MyeongKi.
 */
data class UpdateLessonRequestOption(
    val lessonId: Long,
    val studentName: String,
    val schoolYear: String,
    val memo: String,
    val lessonSubject: LessonSubject,
    val lessonDay: List<LessonDay>,
    val lessonDuration: LessonDuration,
    val lessonNumberOfProgress: Int,
    val lessonStartDateTime: Long,
    val lessonNumberOfPostpone: Int
)