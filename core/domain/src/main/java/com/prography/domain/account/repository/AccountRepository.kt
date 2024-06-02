package com.prography.domain.account.repository

import com.prography.domain.account.model.AccountInfo
import com.prography.domain.account.request.SignInRequestOption
import kotlinx.coroutines.flow.Flow

/**
 * Created by MyeongKi.
 */
interface AccountRepository {
    fun signIn(requestOption: SignInRequestOption): Flow<AccountInfo>
}