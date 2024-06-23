package com.prography.domain.account.model

/**
 * Created by MyeongKi.
 */
data class AccountInfo(
    val id: Long = -1,
    val accessToken: String,
    val refreshToken: String,
    val tokenType: TokenType = TokenType.ACCESS,
    val email: String = "",
    val status: AccountStatus = AccountStatus.ACTIVE,
    val accountType: AccountType = AccountType.TEACHER,
)