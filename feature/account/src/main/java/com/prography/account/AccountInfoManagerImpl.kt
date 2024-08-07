package com.prography.account

import com.prography.domain.account.AccountInfoManager
import com.prography.domain.account.model.AccountInfo
import com.prography.domain.account.model.AccountRole
import com.prography.domain.account.model.AccountStatus
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
    private lateinit var accountPreference: AccountPreference
    private lateinit var notifyAccountChange: () -> Unit
    override fun init(
        accessTokenHelper: CryptoHelper,
        refreshTokenHelper: CryptoHelper,
        accountPreference: AccountPreference,
        notifyAccountChange: () -> Unit
    ) {
        this.accessTokenHelper = accessTokenHelper
        this.refreshTokenHelper = refreshTokenHelper
        this.accountPreference = accountPreference
        this.notifyAccountChange = notifyAccountChange
        this.accountInfo.set(
            AccountInfo(
                accessToken = accessTokenHelper.decryptContents() ?: "",
                refreshToken = refreshTokenHelper.decryptContents() ?: "",
                status = accountPreference.getAccountStatus(),
                role = accountPreference.getAccountRole()
            )
        )
    }


    override fun update(accountInfo: AccountInfo) {
        accessTokenHelper.encryptContentsAndStore(accountInfo.accessToken)
        refreshTokenHelper.encryptContentsAndStore(accountInfo.refreshToken)
        accountPreference.setAccountStatus(accountInfo.status)
        accountPreference.setAccountRole(accountInfo.role)
        this.accountInfo.set(accountInfo)
        notifyAccountChange()
    }

    override fun updateRefreshToken(refreshToken: RefreshToken) {
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
        accessTokenHelper.encryptContentsAndStore("")
        refreshTokenHelper.encryptContentsAndStore("")
        accountPreference.setAccountStatus(AccountStatus.NONE)
        accountPreference.setAccountRole(AccountRole.NONE)

    }

    override fun isRequireSyncAccountInfo(): Boolean {
        return accountInfo.get()?.id == -1L || accountInfo.get() == null
    }
}