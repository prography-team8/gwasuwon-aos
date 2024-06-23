package com.prography.domain.account.request

import com.prography.domain.account.model.AccountType
import com.prography.domain.account.model.SocialLoginType

/**
 * Created by MyeongKi.
 */
data class SignUpRequestOption(
    val accountType: AccountType
)