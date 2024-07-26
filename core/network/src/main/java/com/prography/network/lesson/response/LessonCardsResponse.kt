package com.prography.network.lesson.response

import kotlinx.serialization.Serializable

/**
 * Created by MyeongKi.
 */
@Serializable
data class LessonCardsResponse(
    val content: List<LessonCardResponse>,
    val numberOfElements: Int,
)

@Serializable
data class LessonCardResponse(
    val id: Long,
    val studentName: String,
    val grade: String,
    val subject: String,
    val classDays: List<String>,
    val numberOfSessionsCompleted: Int,
    val numberOfSessions: Int,
    val sessionDuration: String
)