package com.prography.lesson

import com.prography.usm.action.ActionEvent
import com.prography.usm.action.Intent

/**
 * Created by MyeongKi.
 */
sealed interface SuccessCreateLessonIntent : Intent<SuccessCreateLessonActionEvent> {
    data object ClickLessonContract : SuccessCreateLessonIntent
    data object ClickLessonDetail:SuccessCreateLessonIntent
    override fun toActionEvent(): SuccessCreateLessonActionEvent {
        return when (this) {

            is ClickLessonContract -> {
                SuccessCreateLessonActionEvent.CopyLessonContractQr
            }
            is ClickLessonDetail -> {
                SuccessCreateLessonActionEvent.NavigateLessonDetail
            }
        }
    }
}

sealed interface SuccessCreateLessonActionEvent : ActionEvent {
    data object CopyLessonContractQr : SuccessCreateLessonActionEvent
    data object NavigateLessonDetail : SuccessCreateLessonActionEvent
    data object LoadLessonContractUrl : SuccessCreateLessonActionEvent
}