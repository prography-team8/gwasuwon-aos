package com.prography.network.lesson.body

import kotlinx.serialization.Serializable

/**
 * Created by MyeongKi.
 */
@Serializable
data class JoinLessonRequestBody(
    val classId: Long
)