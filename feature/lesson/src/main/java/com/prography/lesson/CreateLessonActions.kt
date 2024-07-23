package com.prography.lesson

import com.prography.domain.lesson.model.LessonDay
import com.prography.domain.lesson.model.LessonDuration
import com.prography.domain.lesson.model.LessonSubject
import com.prography.usm.action.ActionEvent
import com.prography.usm.action.Intent

/**
 * Created by MyeongKi.
 */
sealed interface CreateLessonIntent : Intent<CreateLessonActionEvent> {
    data object ClickNext : CreateLessonIntent
    data object ClickCreateLesson : CreateLessonIntent
    data object ClickBack : CreateLessonIntent
    data class ClickLessonDay(val lessonDay: LessonDay) : CreateLessonIntent
    data class ClickLessonDuration(val lessonDuration: LessonDuration) : CreateLessonIntent
    data class ClickLessonNumberOfPostpone(val lessonNumberOfPostpone: Int) : CreateLessonIntent
    data class ClickLessonSubject(val lessonSubject: LessonSubject) : CreateLessonIntent
    data class ClickLessonDate(val lessonStartDateTime: Long) : CreateLessonIntent
    data object ClickPostponeInformationIcon : CreateLessonIntent
    data object ClickDialog : CreateLessonIntent

    override fun toActionEvent(): CreateLessonActionEvent {
        return when (this) {
            is ClickLessonDate -> {
                CreateLessonActionEvent.UpdateLessonStartDate(lessonStartDateTime)
            }

            is ClickLessonDuration -> {
                CreateLessonActionEvent.UpdateLessonDuration(lessonDuration)
            }

            is ClickLessonSubject -> {
                CreateLessonActionEvent.UpdateLessonSubject(lessonSubject)
            }

            is ClickLessonNumberOfPostpone -> {
                CreateLessonActionEvent.UpdateLessonNumberOfPostpone(lessonNumberOfPostpone)
            }

            is ClickLessonDay -> {
                CreateLessonActionEvent.ToggleLessonDay(lessonDay)
            }

            is ClickNext -> {
                CreateLessonActionEvent.GoToNextPage
            }

            is ClickCreateLesson -> {
                CreateLessonActionEvent.CreateLesson
            }

            is ClickBack -> {
                CreateLessonActionEvent.PopBack
            }

            is ClickPostponeInformationIcon -> {
                CreateLessonActionEvent.ShowPostponeInformationDialog
            }

            is ClickDialog -> {
                CreateLessonActionEvent.HideDialog
            }
        }
    }
}

sealed interface CreateLessonActionEvent : ActionEvent {
    data object GoToNextPage : CreateLessonActionEvent
    data object PopBack : CreateLessonActionEvent
    data object CreateLesson : CreateLessonActionEvent

    data class UpdateStudentName(
        val studentName: String
    ) : CreateLessonActionEvent

    data class UpdateSchoolYear(
        val schoolYear: String
    ) : CreateLessonActionEvent

    data class UpdateMemo(
        val memo: String
    ) : CreateLessonActionEvent

    data class UpdateLessonSubject(
        val lessonSubject: LessonSubject
    ) : CreateLessonActionEvent

    data class UpdateLessonDuration(
        val lessonDuration: LessonDuration
    ) : CreateLessonActionEvent

    data class ToggleLessonDay(
        val lessonDay: LessonDay
    ) : CreateLessonActionEvent

    data class UpdateLessonNumberOfProgress(
        val lessonNumberOfProgress: Int
    ) : CreateLessonActionEvent

    data class UpdateLessonNumberOfPostpone(
        val lessonNumberOfPostpone: Int
    ) : CreateLessonActionEvent

    data class UpdateLessonStartDate(
        val lessonStartDateTime: Long
    ) : CreateLessonActionEvent

    data object ShowPostponeInformationDialog : CreateLessonActionEvent
    data object HideDialog : CreateLessonActionEvent
}