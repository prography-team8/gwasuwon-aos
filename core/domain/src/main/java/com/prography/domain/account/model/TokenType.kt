package com.prography.domain.account.model

/**
 * Created by MyeongKi.
 */
enum class TokenType(value: String) {
    ACCESS("accessToken"),
    REFRESH("refreshToken"),
    ;
}