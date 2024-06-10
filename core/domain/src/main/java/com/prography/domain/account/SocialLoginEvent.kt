package com.prography.domain.account

import com.prography.domain.account.model.SocialLoginType

/**
 * Created by MyeongKi.
 */
sealed interface SocialLoginEvent {
    data class GetOnSuccessSocialLoginAccessKey(val type: SocialLoginType, val accessKey: String) : SocialLoginEvent
    data class GetOnFailSocialLoginAccessKey(val type: SocialLoginType) : SocialLoginEvent
    data class RequestSocialLoginAccessKey(val type: SocialLoginType) : SocialLoginEvent
}