package com.prography.account

import com.prography.usm.action.ActionEvent
import com.prography.usm.action.Intent

/**
 * Created by MyeongKi.
 */
sealed interface SignUpIntent : Intent<SignUpActionEvent> {
    data object ClickAllAgreement : SignUpIntent
    data object ClickPersonalInformationAgreement : SignUpIntent
    data object ClickGwasuwonServiceAgreement : SignUpIntent
    data object ClickTeacher : SignUpIntent
    data object ClickStudent : SignUpIntent
    data object ClickNextButton : SignUpIntent

    override fun toActionEvent(): SignUpActionEvent {
        return when (this) {
            is ClickAllAgreement -> {
                SignUpActionEvent.ToggleAllAgreement
            }

            is ClickPersonalInformationAgreement -> {
                SignUpActionEvent.TogglePersonalInformationAgreement
            }

            is ClickGwasuwonServiceAgreement -> {
                SignUpActionEvent.ToggleGwasuwonServiceAgreement
            }

            is ClickTeacher -> {
                SignUpActionEvent.SelectTeacher
            }

            is ClickStudent -> {
                SignUpActionEvent.SelectStudent
            }

            is ClickNextButton -> {
                SignUpActionEvent.GoToNextSignUpPage
            }
        }
    }
}

sealed interface SignUpActionEvent : ActionEvent {
    data object ToggleAllAgreement : SignUpActionEvent
    data object TogglePersonalInformationAgreement : SignUpActionEvent
    data object ToggleGwasuwonServiceAgreement : SignUpActionEvent
    data object SelectTeacher : SignUpActionEvent
    data object SelectStudent : SignUpActionEvent
    data object GoToNextSignUpPage : SignUpActionEvent
    data object NavigateLessonRoute : SignUpActionEvent
    data object RequestSignUp : SignUpActionEvent
}
