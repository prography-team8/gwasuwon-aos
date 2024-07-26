package com.prography.lesson

import com.prography.usm.action.ActionEvent
import com.prography.usm.action.Intent

/**
 * Created by MyeongKi.
 */
sealed interface LessonItemIntent : Intent<LessonItemActionEvent> {
    data object ClickLesson : LessonItemIntent
    data object ClickExtensionRequired : LessonItemIntent

    override fun toActionEvent(): LessonItemActionEvent {
        return when (this) {
            is ClickLesson -> {
                LessonItemActionEvent.NavigateLessonDetail
            }

            is ClickExtensionRequired -> {
                LessonItemActionEvent.NavigateExtensionLesson
            }
        }
    }
}

sealed interface LessonItemActionEvent : ActionEvent {
    data object NavigateLessonDetail : LessonItemActionEvent
    data object NavigateExtensionLesson : LessonItemActionEvent
}
