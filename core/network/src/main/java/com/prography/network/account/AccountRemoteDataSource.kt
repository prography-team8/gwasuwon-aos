package com.prography.network.account

import com.prography.domain.account.AccountDataSource
import com.prography.domain.account.AccountInfoManager
import com.prography.domain.account.model.AccountInfo
import com.prography.domain.account.model.AccountRole
import com.prography.domain.account.model.AccountStatus
import com.prography.domain.account.request.SignInRequestOption
import com.prography.domain.account.request.SignUpRequestOption
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by MyeongKi.
 */
class AccountRemoteDataSource(
    private val httpClient: AccountHttpClient,
    private val accountInfoManager: AccountInfoManager,
) : AccountDataSource {
    override fun signIn(requestOption: SignInRequestOption): Flow<AccountInfo> = flow {
        val result = httpClient.requestSignIn(
            requestOption
        )
        emit(
            AccountInfo(
                id = result.id,
                accessToken = result.accessToken,
                email = result.email,
                refreshToken = result.refreshToken,
                role = AccountRole.valueOf(result.role),
                status = AccountStatus.valueOf(result.status),
            )
        )
    }

    override fun signUp(requestOption: SignUpRequestOption): Flow<AccountInfo> {
        return flow {
            val result = httpClient.requestSignUp(
                requestOption
            )
            val refreshToken = accountInfoManager.getAccountInfo()?.refreshToken ?: ""
            val accessToken = accountInfoManager.getAccountInfo()?.accessToken ?: ""
            emit(
                AccountInfo(
                    id = result.id,
                    accessToken = accessToken,
                    email = result.email,
                    refreshToken = refreshToken,
                    role = AccountRole.valueOf(result.role),
                    status = AccountStatus.valueOf(result.status),
                )
            )
        }
    }
}