package com.prography.domain.lesson

import com.prography.domain.lesson.model.Lesson

/**
 * Created by MyeongKi.
 */
sealed interface CommonLessonEvent {
    data class NotifyUpdateLesson(private val lesson: Lesson) : CommonLessonEvent
}