package com.prography.account

import com.prography.domain.account.model.SocialLoginType
import com.prography.usm.action.ActionEvent
import com.prography.usm.action.Intent

/**
 * Created by MyeongKi.
 */
sealed interface SignInIntent : Intent<SignInActionEvent> {
    data object ClickKakaoSignIn : SignInIntent

    override fun toActionEvent(): SignInActionEvent {
        return when (this) {
            is ClickKakaoSignIn -> {
                SignInActionEvent.RequestSocialLoginAccessKey(type = SocialLoginType.KAKAO)
            }
        }
    }
}

sealed interface SignInActionEvent : ActionEvent {
    data class RequestSocialLoginAccessKey(val type: SocialLoginType) : SignInActionEvent
    data object NavigateLessonRoute : SignInActionEvent
    data class NavigateSignUpRoute(
        val type: SocialLoginType,
        val accessKey: String
    ) : SignInActionEvent

    data class RequestSignIn(val type: SocialLoginType, val accessKey: String) : SignInActionEvent
    data class ShowFailSocialLoginFail(val type: SocialLoginType) : SignInActionEvent
}
