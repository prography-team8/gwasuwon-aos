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
    data object ClickInviteStudent : CreateLessonIntent
    data object ClickLessonContract : CreateLessonIntent
    data object ClickHome : CreateLessonIntent
    data object ClickBack : CreateLessonIntent

    override fun toActionEvent(): CreateLessonActionEvent {
        return when (this) {
            is ClickNext -> {
                CreateLessonActionEvent.GoToNextPage
            }

            is ClickBack -> {
                CreateLessonActionEvent.PopBack
            }

            is ClickLessonContract -> {
                CreateLessonActionEvent.NavigateLessonContractQr
            }

            is ClickInviteStudent -> {
                CreateLessonActionEvent.NavigateInviteStudentQr
            }

            is ClickHome -> {
                CreateLessonActionEvent.NavigateHome
            }
        }
    }
}

sealed interface CreateLessonActionEvent : ActionEvent {
    data object GoToNextPage : CreateLessonActionEvent
    data object PopBack : CreateLessonActionEvent
    data object NavigateHome : CreateLessonActionEvent
    data object NavigateLessonContractQr : CreateLessonActionEvent
    data object NavigateInviteStudentQr : CreateLessonActionEvent
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

    data class UpdateLessonStartDate(
        val lessonStartDate: String
    ) : CreateLessonActionEvent
}