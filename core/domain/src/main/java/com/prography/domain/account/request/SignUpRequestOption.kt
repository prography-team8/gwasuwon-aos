package com.prography.domain.account.request

import com.prography.domain.account.model.AccountRole

/**
 * Created by MyeongKi.
 */
data class SignUpRequestOption(
    val roleType: AccountRole
)