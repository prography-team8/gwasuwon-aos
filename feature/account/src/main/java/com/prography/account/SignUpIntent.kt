package com.prography.account

import com.prography.ui.component.CommonDialogIntent
import com.prography.usm.action.ActionEvent
import com.prography.usm.action.Intent

/**
 * Created by MyeongKi.
 */
sealed interface SignUpIntent : Intent<SignUpActionEvent> {
    data object ClickAllAgreement : SignUpIntent
    data object CheckPersonalInformationAgreement : SignUpIntent
    data object ClickArrowPersonalInformationAgreement : SignUpIntent
    data object CheckGwasuwonServiceAgreement : SignUpIntent
    data object ClickArrowGwasuwonServiceAgreement : SignUpIntent
    data object ClickTeacher : SignUpIntent
    data object ClickStudent : SignUpIntent
    data object ClickNextButton : SignUpIntent
    data object ClickStartLesson : SignUpIntent
    data class SignUpCommonDialogIntent(val intent: CommonDialogIntent) : SignUpIntent

    override fun toActionEvent(): SignUpActionEvent {
        return when (this) {
            is ClickAllAgreement -> {
                SignUpActionEvent.ToggleAllAgreement
            }

            is CheckPersonalInformationAgreement -> {
                SignUpActionEvent.TogglePersonalInformationAgreement
            }

            is ClickArrowPersonalInformationAgreement -> {
                SignUpActionEvent.ShowAgreementPage("https://www.naver.com/")
            }

            is CheckGwasuwonServiceAgreement -> {
                SignUpActionEvent.ToggleGwasuwonServiceAgreement
            }

            is ClickArrowGwasuwonServiceAgreement -> {
                SignUpActionEvent.ShowAgreementPage("https://www.naver.com/")
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

            is ClickStartLesson -> {
                SignUpActionEvent.NavigateHome
            }

            is SignUpCommonDialogIntent -> {
                val commonDialogIntent = this.intent
                when (commonDialogIntent) {
                    is CommonDialogIntent.ClickConfirm -> {
                        SignUpActionEvent.HideDialog
                    }

                    is CommonDialogIntent.ClickBackground -> {
                        SignUpActionEvent.HideDialog
                    }
                }
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
    data object NavigateHome : SignUpActionEvent
    data object RequestSignUp : SignUpActionEvent
    data class ShowAgreementPage(val url: String) : SignUpActionEvent
    data object HideDialog : SignUpActionEvent
}
