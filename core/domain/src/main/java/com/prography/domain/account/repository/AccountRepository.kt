package com.prography.domain.account.repository

import com.prography.domain.account.model.AccountInfo
import com.prography.domain.account.request.SignInRequestOption
import com.prography.domain.account.request.SignUpRequestOption
import kotlinx.coroutines.flow.Flow

/**
 * Created by MyeongKi.
 */
interface AccountRepository {
    fun signIn(requestOption: SignInRequestOption): Flow<AccountInfo>
    fun signUp(requestOption: SignUpRequestOption): Flow<AccountInfo>
}