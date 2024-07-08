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

    data object ClickLessonInfoDetail : LessonDetailIntent

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
}
