package com.prography.network.lesson.body

import kotlinx.serialization.Serializable

/**
 * Created by MyeongKi.
 */
@Serializable
class ForceAttendanceLessonRequestBody(
    val scheduleId:Long
)