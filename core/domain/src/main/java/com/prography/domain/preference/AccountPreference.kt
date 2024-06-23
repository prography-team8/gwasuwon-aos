package com.prography.domain.preference

import com.prography.domain.account.model.AccountStatus

/**
 * Created by MyeongKi.
 */
interface AccountPreference {
    fun getAccountStatus():AccountStatus
    fun setAccountStatus(accountStatus: AccountStatus)
}