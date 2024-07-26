package com.prography.lesson

import com.prography.domain.lesson.model.LessonDay
import com.prography.domain.lesson.model.LessonDuration
import com.prography.domain.lesson.model.LessonSubject
import com.prography.ui.component.CommonDialogIntent
import com.prography.usm.action.ActionEvent
import com.prography.usm.action.Intent

/**
 * Created by MyeongKi.
 */
sealed interface LessonInfoDetailIntent : Intent<LessonInfoDetailActionEvent> {
    data object ClickUpdateLesson : LessonInfoDetailIntent
    data object ClickBack : LessonInfoDetailIntent
    data class ClickLessonSubject(val lessonSubject: LessonSubject) : LessonInfoDetailIntent
    data class ClickLessonDuration(val lessonDuration: LessonDuration) : LessonInfoDetailIntent
    data class ClickLessonDay(val lessonDay: LessonDay) : LessonInfoDetailIntent
    data class ClickLessonDate(val lessonStartDateTime: Long) : LessonInfoDetailIntent
    data class ClickLessonNumberOfPostpone(val lessonNumberOfPostpone: Int) : LessonInfoDetailIntent
    data class LessonInfoDetailCommonDialogIntent(val intent: CommonDialogIntent) : LessonInfoDetailIntent

    override fun toActionEvent(): LessonInfoDetailActionEvent {
        return when (this) {
            is ClickLessonDate -> {
                LessonInfoDetailActionEvent.UpdateLessonStartDate(lessonStartDateTime)
            }

            is ClickLessonDuration -> {
                LessonInfoDetailActionEvent.UpdateLessonDuration(lessonDuration)
            }

            is ClickLessonSubject -> {
                LessonInfoDetailActionEvent.UpdateLessonSubject(lessonSubject)
            }

            is ClickLessonDay -> {
                LessonInfoDetailActionEvent.ToggleLessonDay(lessonDay)
            }

            is ClickUpdateLesson -> {
                LessonInfoDetailActionEvent.UpdateLesson
            }

            is ClickBack -> {
                LessonInfoDetailActionEvent.PopBack
            }
            is ClickLessonNumberOfPostpone->{
                LessonInfoDetailActionEvent.UpdateLessonNumberOfPostpone(lessonNumberOfPostpone)
            }
            is LessonInfoDetailCommonDialogIntent -> {
                LessonInfoDetailActionEvent.HideDialog
            }
        }
    }
}

sealed interface LessonInfoDetailActionEvent : ActionEvent {
    data object Refresh : LessonInfoDetailActionEvent
    data object PopBack : LessonInfoDetailActionEvent
    data object UpdateLesson : LessonInfoDetailActionEvent

    data class UpdateStudentName(
        val studentName: String
    ) : LessonInfoDetailActionEvent

    data class UpdateSchoolYear(
        val schoolYear: String
    ) : LessonInfoDetailActionEvent

    data class UpdateLessonSubject(
        val lessonSubject: LessonSubject
    ) : LessonInfoDetailActionEvent

    data class UpdateLessonDuration(
        val lessonDuration: LessonDuration
    ) : LessonInfoDetailActionEvent

    data class ToggleLessonDay(
        val lessonDay: LessonDay
    ) : LessonInfoDetailActionEvent
    data class UpdateLessonNumberOfProgress(
        val lessonNumberOfProgress: Int
    ) : LessonInfoDetailActionEvent
    data class UpdateLessonStartDate(
        val lessonStartDateTime: Long
    ) : LessonInfoDetailActionEvent
    data class UpdateLessonNumberOfPostpone(
        val lessonNumberOfPostpone: Int
    ) : LessonInfoDetailActionEvent

    data object HideDialog : LessonInfoDetailActionEvent
}