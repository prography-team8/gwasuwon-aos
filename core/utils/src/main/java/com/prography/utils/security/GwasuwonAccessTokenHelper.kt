package com.prography.utils.security

import android.content.Context

/**
 * Created by MyeongKi.
 */
class GwasuwonAccessTokenHelper(override val context: Context) : AndroidCryptoHelper() {
    override val key: String = "GwasuwonAccess"
    override val keyEncryptionIv: String = "GwasuwonAccessIv"
    override val fileName: String = "GwasuwonAccessFile"
    override val keyAlias: String = "GwasuwonAccessAlias"

}
class GwasuwonRefreshTokenHelper(override val context: Context) : AndroidCryptoHelper() {
    override val key: String = "GwasuwonRefresh"
    override val keyEncryptionIv: String = "GwasuwonRefreshIv"
    override val fileName: String = "GwasuwonRefreshFile"
    override val keyAlias: String = "GwasuwonRefreshAlias"



}