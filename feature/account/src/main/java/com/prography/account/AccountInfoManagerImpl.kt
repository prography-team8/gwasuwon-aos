package com.prography.account

import com.prography.domain.account.AccountInfoManager
import com.prography.domain.account.model.AccountInfo
import com.prography.domain.account.model.RefreshToken
import com.prography.domain.preference.AccountPreference
import com.prography.utils.security.CryptoHelper
import java.util.concurrent.atomic.AtomicReference

/**
 * Created by MyeongKi.
 */
object AccountInfoManagerImpl : AccountInfoManager {
    private var accountInfo: AtomicReference<AccountInfo?> = AtomicReference()
    private lateinit var accessTokenHelper: CryptoHelper
    private lateinit var refreshTokenHelper: CryptoHelper
    override fun init(
        accessTokenHelper: CryptoHelper,
        refreshTokenHelper: CryptoHelper,
        accountPreference: AccountPreference
    ) {
        this.accessTokenHelper = accessTokenHelper
        this.refreshTokenHelper = refreshTokenHelper
        this.accountInfo.set(
            AccountInfo(
                accessToken = accessTokenHelper.decryptContents() ?: "",
                refreshToken = refreshTokenHelper.decryptContents() ?: "",
                status = accountPreference.getAccountStatus()
            )
        )
    }


    override fun update(accountInfo: AccountInfo) {
        this.accountInfo.set(accountInfo)
    }

    override fun refrehToken(refreshToken: RefreshToken) {
        accessTokenHelper.encryptContentsAndStore(refreshToken.accessToken)
        refreshTokenHelper.encryptContentsAndStore(refreshToken.refreshToken)
        val current = accountInfo.get() ?: AccountInfo(accessToken = "", refreshToken = "")
        accountInfo.set(
            current.copy(
                accessToken = refreshToken.accessToken,
                refreshToken = refreshToken.refreshToken,
            )
        )
    }

    override fun getAccountInfo(): AccountInfo? {
        return accountInfo.get()
    }

    override fun clear() {
        accountInfo.set(null)
    }

    override fun isRequireSyncAccountInfo(): Boolean {
        return accountInfo.get()?.id == -1L || accountInfo.get() == null
    }
}