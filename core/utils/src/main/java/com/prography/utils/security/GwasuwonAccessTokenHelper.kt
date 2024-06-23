package com.prography.utils.security

import android.content.Context

/**
 * Created by MyeongKi.
 */
class GwasuwonAccessTokenHelper(override val context: Context) : AndroidCryptoHelper() {
    override val key: String = "GwasuwonAccess"
}
class GwasuwonRefreshTokenHelper(override val context: Context) : AndroidCryptoHelper() {
    override val key: String = "GwasuwonRefresh"
}