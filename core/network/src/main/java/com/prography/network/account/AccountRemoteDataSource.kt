package com.prography.network.account

import com.prography.domain.account.AccountDataSource
import com.prography.domain.account.exception.NotFoundAccountException
import com.prography.domain.account.model.AccountInfo
import com.prography.domain.account.request.SignInRequestOption
import com.prography.network.NOT_FOUND
import io.ktor.client.plugins.ClientRequestException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by MyeongKi.
 */
class AccountRemoteDataSource(
    private val httpClient: AccountHttpClient
) : AccountDataSource {
    override fun signIn(requestOption: SignInRequestOption): Flow<AccountInfo> = flow {
        try {
            val result = httpClient.requestSignIn(
                requestOption
            )
            emit(
                AccountInfo(
                    userName = result.userName,
                    jwt = result.jwt
                )
            )
        } catch (e: Exception) {
            if (e is ClientRequestException) {
                if (e.response.status.value == NOT_FOUND) {
                    throw NotFoundAccountException()
                }
            }
            throw e
        }
    }
}