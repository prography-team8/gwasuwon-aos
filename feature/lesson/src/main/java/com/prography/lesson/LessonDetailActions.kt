package com.prography.lesson

import com.prography.ui.component.CommonDialogIntent
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
    data object ClickInviteStudent : LessonDetailIntent
    data object ClickLessonInfoDetail : LessonDetailIntent
    data object ClickDeleteLesson : LessonDetailIntent
    data object ClickDialogConfirm : LessonDetailIntent
    data object ClickInavalidLessonDialogConfirm : LessonDetailIntent
    data object ClickInavalidLessonDialogBackground : LessonDetailIntent

    data object ClickDialogCancel : LessonDetailIntent
    data object ClickDeleteDialogConfirm : LessonDetailIntent
    data object ClickDialogBackground : LessonDetailIntent
    data object ClickNotifyLessonDeductedDialog : LessonDetailIntent
    data class LessonDetailCommonDialogIntent(val intent: CommonDialogIntent) : LessonDetailIntent

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
                LessonDetailActionEvent.UpdateForceAttendanceLesson
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

            is ClickDialogConfirm,
            is LessonDetailCommonDialogIntent,
            is ClickDialogCancel,
            is ClickDialogBackground -> {
                LessonDetailActionEvent.HideDialog
            }

            is ClickNotifyLessonDeductedDialog -> {
                LessonDetailActionEvent.HideNotifyLessonDeductedDialog
            }

            is ClickRecognizeQr -> {
                LessonDetailActionEvent.RecognizeQr
            }

            is ClickInviteStudent -> {
                LessonDetailActionEvent.NavigateInviteStudentQr
            }

            is ClickInavalidLessonDialogBackground, is ClickInavalidLessonDialogConfirm -> {
                LessonDetailActionEvent.ClearInvalidLessonDetailScreen
            }
        }
    }
}

fun CommonDialogIntent.toLessonDetailIntent() = LessonDetailIntent.LessonDetailCommonDialogIntent(this)

sealed interface LessonDetailActionEvent : ActionEvent {
    data class Refresh(val nextEvent: LessonDetailActionEvent? = null) : LessonDetailActionEvent
    data object PopBack : LessonDetailActionEvent
    data object NavigateLessonCertificationQr : LessonDetailActionEvent
    data class FocusDate(val date: Long) : LessonDetailActionEvent
    data object UpdateForceAttendanceLesson : LessonDetailActionEvent
    data object NavigateLessonInfoDetail : LessonDetailActionEvent
    data object ShowDeleteLessonDialog : LessonDetailActionEvent
    data object ShowNotifyLessonDeductedDialog : LessonDetailActionEvent
    data object DeleteLesson : LessonDetailActionEvent
    data object HideDialog : LessonDetailActionEvent
    data object HideNotifyLessonDeductedDialog : LessonDetailActionEvent
    data object RecognizeQr : LessonDetailActionEvent
    data object UpdateLessonDeducted : LessonDetailActionEvent
    data class UpdateAttendanceLesson(val qrLessonId: Long) : LessonDetailActionEvent
    data object NavigateInviteStudentQr : LessonDetailActionEvent
    data object ClearInvalidLessonDetailScreen : LessonDetailActionEvent
}
