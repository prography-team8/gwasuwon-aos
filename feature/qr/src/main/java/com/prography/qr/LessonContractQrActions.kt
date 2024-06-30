package com.prography.qr

import com.prography.usm.action.ActionEvent
import com.prography.usm.action.Intent

/**
 * Created by MyeongKi.
 */
sealed interface LessonContractQrIntent : Intent<LessonContractQrActionEvent> {
    data object ClickBack : LessonContractQrIntent
    data object ClickHome : LessonContractQrIntent
    data object ClickShare : LessonContractQrIntent

    override fun toActionEvent(): LessonContractQrActionEvent {
        return when (this) {
            is ClickBack -> {
                LessonContractQrActionEvent.PopBack
            }

            is ClickHome -> {
                LessonContractQrActionEvent.NavigateHome
            }

            is ClickShare -> {
                LessonContractQrActionEvent.CopyQrData
            }
        }
    }
}

sealed interface LessonContractQrActionEvent : ActionEvent {
    data object GenerateQr : LessonContractQrActionEvent
    data object NavigateHome : LessonContractQrActionEvent
    data object CopyQrData : LessonContractQrActionEvent
    data object PopBack : LessonContractQrActionEvent
}