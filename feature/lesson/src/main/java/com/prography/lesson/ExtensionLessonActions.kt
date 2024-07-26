package com.prography.lesson

import com.prography.domain.lesson.model.LessonDay
import com.prography.domain.lesson.model.LessonDuration
import com.prography.domain.lesson.model.LessonSubject
import com.prography.usm.action.ActionEvent
import com.prography.usm.action.Intent

/**
 * Created by MyeongKi.
 */
sealed interface ExtensionLessonIntent : Intent<ExtensionLessonActionEvent> {
    data object ClickExtensionLesson : ExtensionLessonIntent
    data object ClickBack : ExtensionLessonIntent
    data class AdditionalInfo(val additionalInfoIntent: AdditionalInfoIntent) : ExtensionLessonIntent
    data object ClickDialog : ExtensionLessonIntent

    override fun toActionEvent(): ExtensionLessonActionEvent {
        return when (this) {
            is ClickExtensionLesson -> {
                ExtensionLessonActionEvent.ExtensionLesson
            }

            is ClickBack -> {
                ExtensionLessonActionEvent.PopBack
            }

            is ClickDialog -> {
                ExtensionLessonActionEvent.HideDialog
            }

            is AdditionalInfo -> {
                when (val additionalInfoIntent = this.additionalInfoIntent) {
                    is AdditionalInfoIntent.ClickLessonDay -> {
                        ExtensionLessonActionEvent.ToggleLessonDay(additionalInfoIntent.lessonDay)
                    }

                    is AdditionalInfoIntent.ClickLessonDuration -> {
                        ExtensionLessonActionEvent.UpdateLessonDuration(additionalInfoIntent.lessonDuration)
                    }

                    is AdditionalInfoIntent.ClickLessonNumberOfPostpone -> {
                        ExtensionLessonActionEvent.UpdateLessonNumberOfPostpone(additionalInfoIntent.lessonNumberOfPostpone)
                    }

                    is AdditionalInfoIntent.ClickLessonSubject -> {
                        ExtensionLessonActionEvent.UpdateLessonSubject(additionalInfoIntent.lessonSubject)
                    }

                    is AdditionalInfoIntent.ClickLessonDate -> {
                        ExtensionLessonActionEvent.UpdateLessonStartDate(additionalInfoIntent.lessonStartDateTime)
                    }

                    is AdditionalInfoIntent.ClickPostponeInformationIcon -> {
                        ExtensionLessonActionEvent.ShowPostponeInformationDialog
                    }

                    is AdditionalInfoIntent.InputLessonNumberOfProgress -> {
                        ExtensionLessonActionEvent.UpdateLessonNumberOfProgress(additionalInfoIntent.lessonNumberOfProgress)
                    }
                }
            }
        }
    }
}

sealed interface ExtensionLessonActionEvent : ActionEvent {
    data object PopBack : ExtensionLessonActionEvent
    data object ExtensionLesson : ExtensionLessonActionEvent

    data class UpdateLessonSubject(
        val lessonSubject: LessonSubject
    ) : ExtensionLessonActionEvent

    data class UpdateLessonDuration(
        val lessonDuration: LessonDuration
    ) : ExtensionLessonActionEvent

    data class ToggleLessonDay(
        val lessonDay: LessonDay
    ) : ExtensionLessonActionEvent

    data class UpdateLessonNumberOfProgress(
        val lessonNumberOfProgress: Int
    ) : ExtensionLessonActionEvent

    data class UpdateLessonNumberOfPostpone(
        val lessonNumberOfPostpone: Int
    ) : ExtensionLessonActionEvent

    data class UpdateLessonStartDate(
        val lessonStartDateTime: Long
    ) : ExtensionLessonActionEvent

    data object ShowPostponeInformationDialog : ExtensionLessonActionEvent
    data object HideDialog : ExtensionLessonActionEvent
}