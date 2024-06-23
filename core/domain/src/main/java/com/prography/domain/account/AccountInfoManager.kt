package com.prography.domain.account

import com.prography.domain.account.model.AccountInfo
import com.prography.domain.account.model.RefreshToken
import com.prography.domain.preference.AccountPreference
import com.prography.utils.security.CryptoHelper

/**
 * Created by MyeongKi.
 */
interface AccountInfoManager {
    fun init(
        accessTokenHelper: CryptoHelper,
        refreshTokenHelper: CryptoHelper,
        accountPreference: AccountPreference

    )

    fun update(accountInfo: AccountInfo)
    fun refrehToken(refreshToken: RefreshToken)
    fun getAccountInfo(): AccountInfo?
    fun clear()
    fun isRequireSyncAccountInfo(): Boolean
}