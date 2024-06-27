package com.prography.qr

import com.prography.usm.action.ActionEvent
import com.prography.usm.action.Intent

/**
 * Created by MyeongKi.
 */
sealed interface LessonContractQrIntent : Intent<LessonContractQrActionEvent> {
    data object ClickBack : LessonContractQrIntent

    override fun toActionEvent(): LessonContractQrActionEvent {
        return when (this) {
            is ClickBack -> {
                LessonContractQrActionEvent.PopBack
            }
        }
    }
}

sealed interface LessonContractQrActionEvent : ActionEvent {
    data object GenerateQr : LessonContractQrActionEvent
    data object PopBack : LessonContractQrActionEvent
}