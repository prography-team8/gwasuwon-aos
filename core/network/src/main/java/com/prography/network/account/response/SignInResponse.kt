package com.prography.network.account.response

import kotlinx.serialization.Serializable

/**
 * Created by MyeongKi.
 */
@Serializable
data class SignInResponse(
    val id: Long,
    val email: String,
    val tokenType: String,
    val accessToken: String,
    val refreshToken: String,
    val status: String,
    val role: String,
)