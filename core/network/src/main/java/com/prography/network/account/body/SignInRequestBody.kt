package com.prography.network.account.body

import kotlinx.serialization.Serializable

/**
 * Created by MyeongKi.
 */
@Serializable
data class SignInRequestBody(
    val accessToken: String,
)