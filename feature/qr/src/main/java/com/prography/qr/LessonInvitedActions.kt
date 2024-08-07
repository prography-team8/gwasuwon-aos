package com.prography.qr

import com.prography.usm.action.ActionEvent
import com.prography.usm.action.Intent

/**
 * Created by MyeongKi.
 */
sealed interface LessonInvitedIntent : Intent<LessonInvitedActionEvent> {
    data object ClickQrRecognition : LessonInvitedIntent
    data object ClickNavigateCalendar : LessonInvitedIntent
    data object ClickDialog : LessonInvitedIntent

    override fun toActionEvent(): LessonInvitedActionEvent {
        return when (this) {
            is ClickQrRecognition -> {
                LessonInvitedActionEvent.StartQrRecognition
            }

            is ClickNavigateCalendar -> {
                LessonInvitedActionEvent.NavigateLessonDetail
            }
            is ClickDialog -> {
                LessonInvitedActionEvent.HideDialog
            }
        }
    }
}

sealed interface LessonInvitedActionEvent : ActionEvent {
    data object StartQrRecognition : LessonInvitedActionEvent
    data object NavigateLessonDetail : LessonInvitedActionEvent
    data class ParticipateLesson(val lessonId: Long) : LessonInvitedActionEvent
    data object HideDialog : LessonInvitedActionEvent
}