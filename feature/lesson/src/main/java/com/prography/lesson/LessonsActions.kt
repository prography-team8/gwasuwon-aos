package com.prography.lesson

import com.prography.usm.action.ActionEvent
import com.prography.usm.action.Intent

/**
 * Created by MyeongKi.
 */
sealed interface LessonsIntent : Intent<LessonsActionEvent> {
    data object ClickCreateLesson : LessonsIntent
    data class ClickLesson(val lessonsId: Long) : LessonsIntent

    override fun toActionEvent(): LessonsActionEvent {
        return when (this) {
            is ClickCreateLesson -> {
                LessonsActionEvent.NavigateCreateLesson
            }
            is ClickLesson -> {
                LessonsActionEvent.NavigateLessonDetail(lessonsId)
            }
        }
    }
}

sealed interface LessonsActionEvent : ActionEvent {
    data class NavigateLessonDetail(val lessonId: Long) : LessonsActionEvent
    data object NavigateCreateLesson : LessonsActionEvent
}
