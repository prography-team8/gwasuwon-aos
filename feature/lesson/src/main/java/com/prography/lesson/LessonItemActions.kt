package com.prography.lesson

import com.prography.usm.action.ActionEvent
import com.prography.usm.action.Intent

/**
 * Created by MyeongKi.
 */
sealed interface LessonItemIntent : Intent<LessonItemActionEvent> {
    data object ClickLesson : LessonItemIntent

    override fun toActionEvent(): LessonItemActionEvent {
        return when (this) {
            is ClickLesson -> {
                LessonItemActionEvent.NavigateLessonDetail
            }
        }
    }
}

sealed interface LessonItemActionEvent : ActionEvent {
    data object NavigateLessonDetail : LessonItemActionEvent
}
