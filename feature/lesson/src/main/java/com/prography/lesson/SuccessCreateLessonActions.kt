package com.prography.lesson

import com.prography.usm.action.ActionEvent
import com.prography.usm.action.Intent

/**
 * Created by MyeongKi.
 */
sealed interface SuccessCreateLessonIntent : Intent<SuccessCreateLessonActionEvent> {
    data object ClickLessonInfoDetail : SuccessCreateLessonIntent
    data object ClickInviteStudent : SuccessCreateLessonIntent
    data object ClickLessonContract : SuccessCreateLessonIntent
    data object ClickHome : SuccessCreateLessonIntent

    override fun toActionEvent(): SuccessCreateLessonActionEvent {
        return when (this) {
            is ClickLessonInfoDetail -> {
                SuccessCreateLessonActionEvent.NavigateLessonInfoDetail
            }

            is ClickLessonContract -> {
                SuccessCreateLessonActionEvent.NavigateLessonContractQr
            }

            is ClickInviteStudent -> {
                SuccessCreateLessonActionEvent.NavigateInviteStudentQr
            }

            is ClickHome -> {
                SuccessCreateLessonActionEvent.NavigateHome
            }
        }
    }
}

sealed interface SuccessCreateLessonActionEvent : ActionEvent {
    data object NavigateHome : SuccessCreateLessonActionEvent
    data object NavigateLessonContractQr : SuccessCreateLessonActionEvent
    data object NavigateInviteStudentQr : SuccessCreateLessonActionEvent
    data object NavigateLessonInfoDetail : SuccessCreateLessonActionEvent
}