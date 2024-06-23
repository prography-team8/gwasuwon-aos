package com.prography.domain.account.model

/**
 * Created by MyeongKi.
 */
data class RefreshToken(
    val refreshToken: String,
    val accessToken: String,
)