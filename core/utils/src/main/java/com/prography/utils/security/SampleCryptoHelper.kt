package com.prography.utils.security

import android.content.Context

/**
 * Created by MyeongKi.
 */
class SampleCryptoHelper(override val context: Context) : AndroidCryptoHelper() {
    override val key: String = "SAMPLE"
}