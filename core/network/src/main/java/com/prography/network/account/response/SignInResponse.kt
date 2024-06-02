package com.prography.network.account.response

import kotlinx.serialization.Serializable

/**
 * Created by MyeongKi.
 */
//FIXME
@Serializable
data class SignInResponse(
    val userName: String,
    val jwt: String
)