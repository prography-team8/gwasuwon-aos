package com.prography.utils.security

import android.content.Context

/**
 * Created by MyeongKi.
 */
class GwasuwonCryptoHelper(override val context: Context) : AndroidCryptoHelper() {
    override val key: String = "GwasuwonJwt"
}