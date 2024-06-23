package com.prography.network.account.body

import kotlinx.serialization.Serializable

/**
 * Created by MyeongKi.
 */
@Serializable
data class SignUpRequestBody(
    val accountType: String,
)