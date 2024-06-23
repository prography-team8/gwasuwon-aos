package com.prography.network.account

import com.prography.domain.account.AccountDataSource
import com.prography.domain.account.model.AccountInfo
import com.prography.domain.account.model.AccountStatus
import com.prography.domain.account.model.AccountType
import com.prography.domain.account.model.TokenType
import com.prography.domain.account.request.SignInRequestOption
import com.prography.domain.account.request.SignUpRequestOption
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by MyeongKi.
 */
class AccountRemoteDataSource(
    private val httpClient: AccountHttpClient
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
                accountType = AccountType.valueOf(result.accountType),
                status = AccountStatus.valueOf(result.status),
                tokenType = TokenType.valueOf(result.tokenType)
            )
        )
    }

    override fun signUp(requestOption: SignUpRequestOption): Flow<AccountInfo> {
        return flow {
            val result = httpClient.requestSignUp(
                requestOption
            )
            emit(
                AccountInfo(
                    id = result.id,
                    accessToken = result.accessToken,
                    email = result.email,
                    refreshToken = result.refreshToken,
                    accountType = AccountType.valueOf(result.accountType),
                    status = AccountStatus.valueOf(result.status),
                    tokenType = TokenType.valueOf(result.tokenType)
                )
            )
        }
    }
}