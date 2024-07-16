package com.prography.domain.account.model

/**
 * Created by MyeongKi.
 */
data class AccountInfo(
    val id: Long = -1,
    val accessToken: String,
    val refreshToken: String,
    val email: String = "",
    val status: AccountStatus = AccountStatus.ACTIVE,
    val role: AccountRole = AccountRole.TEACHER,
)