package com.prography.domain.account.model

/**
 * Created by MyeongKi.
 */
data class AccountInfo(
    val userName: String,
    val accountType: AccountType,
    val jwt: String,
)