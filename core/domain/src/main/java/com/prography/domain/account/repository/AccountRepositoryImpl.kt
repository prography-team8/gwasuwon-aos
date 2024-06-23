package com.prography.domain.account.repository

import com.prography.domain.account.AccountDataSource
import com.prography.domain.account.model.AccountInfo
import com.prography.domain.account.request.SignInRequestOption
import kotlinx.coroutines.flow.Flow

/**
 * Created by MyeongKi.
 */
class AccountRepositoryImpl(
    private val remoteDataSource: AccountDataSource
) : AccountRepository {
    override fun signIn(requestOption: SignInRequestOption): Flow<AccountInfo> {
        return remoteDataSource.signIn(requestOption)
    }
}