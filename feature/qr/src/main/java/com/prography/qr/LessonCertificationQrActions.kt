package com.prography.qr

import com.prography.usm.action.ActionEvent
import com.prography.usm.action.Intent

/**
 * Created by MyeongKi.
 */

sealed interface LessonCertificationQrIntent : Intent<LessonCertificationQrActionEvent> {
    data object ClickBack : LessonCertificationQrIntent

    override fun toActionEvent(): LessonCertificationQrActionEvent {
        return when (this) {
            is ClickBack -> {
                LessonCertificationQrActionEvent.PopBack
            }
        }
    }
}

sealed interface LessonCertificationQrActionEvent : ActionEvent {
    data object GenerateQr : LessonCertificationQrActionEvent
    data object PopBack : LessonCertificationQrActionEvent
}