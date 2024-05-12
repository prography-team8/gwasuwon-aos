package com.prography.utils.security

/**
 * Created by MyeongKi.
 */
interface CryptoHelper {
    fun encryptContentsAndStore(contents: String)
    fun decryptContents(): String?
}