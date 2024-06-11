package com.prography.domain.account.request

import com.prography.domain.account.model.SocialLoginType

/**
 * Created by MyeongKi.
 */
data class SignInRequestOption (
    val type: SocialLoginType,
    val accessKey: String
)