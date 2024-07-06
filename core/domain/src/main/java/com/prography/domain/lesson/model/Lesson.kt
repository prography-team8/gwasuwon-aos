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
    val lessonNumberOfPostpone: Int,
    val lessonStartDateTime: Long,
    val restLesson: Int,
    val lessonContractUrl: String
)