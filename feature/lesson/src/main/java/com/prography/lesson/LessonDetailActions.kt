package com.prography.lesson

import com.prography.usm.action.ActionEvent
import com.prography.usm.action.Intent

/**
 * Created by MyeongKi.
 */
sealed interface LessonDetailIntent : Intent<LessonDetailActionEvent> {
    data object ClickBack : LessonDetailIntent
    data object ClickLessonCertificationQr : LessonDetailIntent
    data class ClickDate(val date: Long) : LessonDetailIntent
    data object ClickCheckByAttendance : LessonDetailIntent
    data object ClickRecognizeQr : LessonDetailIntent
    data object ClickLessonInfoDetail : LessonDetailIntent
    data object ClickDeleteLesson : LessonDetailIntent
    data object ClickDialogCancel : LessonDetailIntent
    data object ClickDeleteDialogConfirm : LessonDetailIntent
    data object ClickDialogBackground : LessonDetailIntent
    data object ClickNotifyLessonDeductedDialog : LessonDetailIntent

    override fun toActionEvent(): LessonDetailActionEvent {
        return when (this) {
            is ClickBack -> {
                LessonDetailActionEvent.PopBack
            }

            is ClickLessonCertificationQr -> {
                LessonDetailActionEvent.NavigateLessonCertificationQr
            }

            is ClickDate -> {
                LessonDetailActionEvent.FocusDate(date)
            }

            is ClickCheckByAttendance -> {
                LessonDetailActionEvent.CheckByAttendance
            }

            is ClickLessonInfoDetail -> {
                LessonDetailActionEvent.NavigateLessonInfoDetail
            }

            is ClickDeleteLesson -> {
                LessonDetailActionEvent.ShowDeleteLessonDialog
            }

            is ClickDeleteDialogConfirm -> {
                LessonDetailActionEvent.DeleteLesson
            }

            is ClickDialogCancel, is ClickDialogBackground -> {
                LessonDetailActionEvent.HideDialog
            }

            is ClickNotifyLessonDeductedDialog -> {
                LessonDetailActionEvent.HideNotifyLessonDeductedDialog
            }

            is ClickRecognizeQr -> {
                LessonDetailActionEvent.RecognizeQr
            }
        }
    }
}

sealed interface LessonDetailActionEvent : ActionEvent {
    data object Refresh : LessonDetailActionEvent
    data object PopBack : LessonDetailActionEvent
    data object NavigateLessonCertificationQr : LessonDetailActionEvent
    data class FocusDate(val date: Long) : LessonDetailActionEvent
    data object CheckByAttendance : LessonDetailActionEvent
    data object NavigateLessonInfoDetail : LessonDetailActionEvent
    data object ShowDeleteLessonDialog : LessonDetailActionEvent
    data object ShowNotifyLessonDeductedDialog : LessonDetailActionEvent
    data object DeleteLesson : LessonDetailActionEvent
    data object HideDialog : LessonDetailActionEvent
    data object HideNotifyLessonDeductedDialog : LessonDetailActionEvent
    data object RecognizeQr : LessonDetailActionEvent
    data object UpdateLessonDeducted : LessonDetailActionEvent
    data class CertificateLesson(val qrLessonId: Long) : LessonDetailActionEvent
}
