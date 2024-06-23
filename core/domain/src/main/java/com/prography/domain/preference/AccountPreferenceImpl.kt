package com.prography.domain.preference

import android.app.Application
import com.prography.domain.account.model.AccountStatus

/**
 * Created by MyeongKi.
 */
class AccountPreferenceImpl(private val context: Application) : AccountPreference {
    override fun getAccountStatus(): AccountStatus {
        return try {
            AccountStatus.valueOf(context.getString(KEY_ACCOUNT_STATUS) ?: "")
        } catch (e: Exception) {
            AccountStatus.NONE
        }
    }

    override fun setAccountStatus(accountStatus: AccountStatus) {
        context.putString(KEY_ACCOUNT_STATUS, accountStatus.name)

    }

    companion object {
        private const val KEY_ACCOUNT_STATUS = "accountStatus"
    }
}