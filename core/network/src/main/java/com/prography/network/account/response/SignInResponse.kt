package com.prography.network.account.response

import kotlinx.serialization.Serializable

/**
 * Created by MyeongKi.
 */
@Serializable
data class SignInResponse(
    val id: Long,
    val accessToken: String,
    val tokenType: String,
    val email: String,
    val status: String,
    val refreshToken: String,
    val accountType: String,
)