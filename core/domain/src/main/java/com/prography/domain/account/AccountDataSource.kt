package com.prography.domain.account

import com.prography.domain.account.model.AccountInfo
import com.prography.domain.account.model.SocialLoginType
import com.prography.domain.account.request.SignInRequestOption
import kotlinx.coroutines.flow.Flow

/**
 * Created by MyeongKi.
 */
interface AccountDataSource {
    fun signIn(requestOption: SignInRequestOption): Flow<AccountInfo>
}