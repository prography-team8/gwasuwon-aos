package com.prography.domain.lesson.model

/**
 * Created by MyeongKi.
 */
data class Lesson(
    val lessonId: Long,
    val studentName: String,
    val schoolYear: String,
    val memo: String,
    val lessonSubject: LessonSubject,
    val lessonDay: List<LessonDay>,
    val lessonDuration: LessonDuration,
    val lessonNumberOfProgress: Int,
    val lessonStartDate: String,
    val restLesson: Int,
    val lessonContractUrl: String
)