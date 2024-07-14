package com.prography.network.account.response

import kotlinx.serialization.Serializable

/**
 * Created by MyeongKi.
 */
@Serializable
data class RefreshResponse(
    val accessToken: String,
    val refreshToken: String
)