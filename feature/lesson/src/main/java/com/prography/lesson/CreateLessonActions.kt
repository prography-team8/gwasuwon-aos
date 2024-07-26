package com.prography.lesson

import com.prography.domain.lesson.model.LessonDay
import com.prography.domain.lesson.model.LessonDuration
import com.prography.domain.lesson.model.LessonSubject
import com.prography.usm.action.ActionEvent
import com.prography.usm.action.Intent

/**
 * Created by MyeongKi.
 */
sealed interface AdditionalInfoIntent {
    data class ClickLessonDay(val lessonDay: LessonDay) : AdditionalInfoIntent
    data class ClickLessonDuration(val lessonDuration: LessonDuration) : AdditionalInfoIntent
    data class ClickLessonNumberOfPostpone(val lessonNumberOfPostpone: Int) : AdditionalInfoIntent
    data class ClickLessonSubject(val lessonSubject: LessonSubject) : AdditionalInfoIntent
    data class ClickLessonDate(val lessonStartDateTime: Long) : AdditionalInfoIntent
    data object ClickPostponeInformationIcon : AdditionalInfoIntent
    data class InputLessonNumberOfProgress(val lessonNumberOfProgress: Int) : AdditionalInfoIntent
}

sealed interface CreateLessonIntent : Intent<CreateLessonActionEvent> {
    data object ClickNext : CreateLessonIntent
    data object ClickCreateLesson : CreateLessonIntent
    data object ClickBack : CreateLessonIntent
    data class AdditionalInfo(val additionalInfoIntent: AdditionalInfoIntent) : CreateLessonIntent
    data object ClickDialog : CreateLessonIntent

    override fun toActionEvent(): CreateLessonActionEvent {
        return when (this) {
            is ClickNext -> {
                CreateLessonActionEvent.GoToNextPage
            }

            is ClickCreateLesson -> {
                CreateLessonActionEvent.CreateLesson
            }

            is ClickBack -> {
                CreateLessonActionEvent.PopBack
            }
            is ClickDialog -> {
                CreateLessonActionEvent.HideDialog
            }
            is AdditionalInfo -> {
                when(val additionalInfoIntent = this.additionalInfoIntent){
                    is AdditionalInfoIntent.ClickLessonDay -> {
                        CreateLessonActionEvent.ToggleLessonDay(additionalInfoIntent.lessonDay)
                    }
                    is AdditionalInfoIntent.ClickLessonDuration -> {
                        CreateLessonActionEvent.UpdateLessonDuration(additionalInfoIntent.lessonDuration)
                    }
                    is AdditionalInfoIntent.ClickLessonNumberOfPostpone -> {
                        CreateLessonActionEvent.UpdateLessonNumberOfPostpone(additionalInfoIntent.lessonNumberOfPostpone)
                    }
                    is AdditionalInfoIntent.ClickLessonSubject -> {
                        CreateLessonActionEvent.UpdateLessonSubject(additionalInfoIntent.lessonSubject)
                    }
                    is AdditionalInfoIntent.ClickLessonDate -> {
                        CreateLessonActionEvent.UpdateLessonStartDate(additionalInfoIntent.lessonStartDateTime)
                    }
                    is AdditionalInfoIntent.ClickPostponeInformationIcon -> {
                        CreateLessonActionEvent.ShowPostponeInformationDialog
                    }

                    is AdditionalInfoIntent.InputLessonNumberOfProgress -> {
                        CreateLessonActionEvent.UpdateLessonNumberOfProgress(additionalInfoIntent.lessonNumberOfProgress)
                    }
                }
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