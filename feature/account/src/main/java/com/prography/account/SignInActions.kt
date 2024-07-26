package com.prography.account

import com.prography.domain.account.model.SocialLoginType
import com.prography.ui.component.CommonDialogIntent
import com.prography.usm.action.ActionEvent
import com.prography.usm.action.Intent

/**
 * Created by MyeongKi.
 */
sealed interface SignInIntent : Intent<SignInActionEvent> {
    data object ClickKakaoSignIn : SignInIntent
    data class SignInCommonDialogIntent(val intent: CommonDialogIntent) : SignInIntent

    override fun toActionEvent(): SignInActionEvent {
        return when (this) {
            is ClickKakaoSignIn -> {
                SignInActionEvent.RequestSocialLoginAccessKey(type = SocialLoginType.KAKAO)
            }

            is SignInCommonDialogIntent -> {
                val commonDialogIntent = this.intent
                when (commonDialogIntent) {
                    is CommonDialogIntent.ClickConfirm -> {
                        SignInActionEvent.HideDialog
                    }

                    is CommonDialogIntent.ClickBackground -> {
                        SignInActionEvent.HideDialog
                    }
                }
            }
        }
    }
}

sealed interface SignInActionEvent : ActionEvent {
    data class RequestSocialLoginAccessKey(val type: SocialLoginType) : SignInActionEvent
    data object NavigateLessonRoute : SignInActionEvent
    data object NavigateSignUpRoute : SignInActionEvent
    data class RequestSignIn(val type: SocialLoginType, val accessKey: String) : SignInActionEvent
    data class ShowFailSocialLoginFail(val type: SocialLoginType) : SignInActionEvent
    data object HideDialog : SignInActionEvent
}
