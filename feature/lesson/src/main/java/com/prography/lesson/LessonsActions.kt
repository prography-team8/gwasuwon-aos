package com.prography.lesson

import com.prography.usm.action.ActionEvent
import com.prography.usm.action.Intent

/**
 * Created by MyeongKi.
 */
sealed interface LessonsIntent : Intent<LessonsActionEvent> {
    data object ClickCreateLesson : LessonsIntent

    override fun toActionEvent(): LessonsActionEvent {
        return when (this) {
            is ClickCreateLesson -> {
                LessonsActionEvent.NavigateCreateLesson
            }
        }
    }
}

sealed interface LessonsActionEvent : ActionEvent {
    data object RequestRefresh : LessonsActionEvent
    data object OnStartRefresh : LessonsActionEvent
    data object NavigateCreateLesson : LessonsActionEvent
}
