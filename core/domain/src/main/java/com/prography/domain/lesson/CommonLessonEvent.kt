package com.prography.domain.lesson

import com.prography.domain.lesson.model.Lesson

/**
 * Created by MyeongKi.
 */
sealed interface CommonLessonEvent {
    data class NotifyUpdateLesson(val lesson: Lesson) : CommonLessonEvent
    data class NotifyDeleteLesson(val lessonId: Long) : CommonLessonEvent
}