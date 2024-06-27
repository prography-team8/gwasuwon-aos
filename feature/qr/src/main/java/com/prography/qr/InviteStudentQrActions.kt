package com.prography.qr

import com.prography.usm.action.ActionEvent
import com.prography.usm.action.Intent

/**
 * Created by MyeongKi.
 */
sealed interface InviteStudentQrIntent : Intent<InviteStudentQrActionEvent> {
    data object ClickBack : InviteStudentQrIntent
    data object ClickHome : InviteStudentQrIntent

    override fun toActionEvent(): InviteStudentQrActionEvent {
        return when (this) {
            is ClickBack -> {
                InviteStudentQrActionEvent.PopBack
            }

            is ClickHome -> {
                InviteStudentQrActionEvent.NavigateHome
            }
        }
    }
}

sealed interface InviteStudentQrActionEvent : ActionEvent {
    data object GenerateQr : InviteStudentQrActionEvent
    data object NavigateHome : InviteStudentQrActionEvent
    data object PopBack : InviteStudentQrActionEvent
}