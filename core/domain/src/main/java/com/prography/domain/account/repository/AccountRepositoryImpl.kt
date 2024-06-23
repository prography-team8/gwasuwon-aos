package com.prography.domain.account.repository

import com.prography.domain.account.AccountDataSource
import com.prography.domain.account.model.AccountInfo
import com.prography.domain.account.request.SignInRequestOption
import com.prography.domain.account.request.SignUpRequestOption
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

    override fun signUp(requestOption: SignUpRequestOption): Flow<AccountInfo> {
        return remoteDataSource.signUp(requestOption)
    }
}