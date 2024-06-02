package com.prography.domain.account.exception

import com.prography.domain.account.model.SocialLoginType

/**
 * Created by MyeongKi.
 */
data class NotFoundAccountException(
    val type: SocialLoginType,
    val accessKey: String
) : Exception()