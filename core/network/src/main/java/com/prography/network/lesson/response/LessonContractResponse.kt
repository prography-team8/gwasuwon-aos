package com.prography.network.lesson.response

import kotlinx.serialization.Serializable

/**
 * Created by MyeongKi.
 */
@Serializable
data class LessonContractResponse(
    val contractLink: String
)