package com.prography.network.account

import com.prography.domain.account.request.SignInRequestOption
import com.prography.domain.account.request.SignUpRequestOption
import com.prography.network.GWASUWON_HOST
import com.prography.network.account.body.SignInRequestBody
import com.prography.network.account.body.SignUpRequestBody
import com.prography.network.account.response.SignInResponse
import com.prography.network.setJsonBody
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post

/**
 * Created by MyeongKi.
 */
class AccountHttpClient(private val httpClient: HttpClient) {
    suspend fun requestSignIn(requestOption: SignInRequestOption): SignInResponse {
        return httpClient.post("$GWASUWON_HOST/api/v1/auth/login/${requestOption.type.name}") {
            setJsonBody(
                SignInRequestBody(accessToken = requestOption.accessKey)
            )
        }.body()
    }
    suspend fun requestSignUp(requestOption: SignUpRequestOption): SignInResponse {
        return httpClient.post("$GWASUWON_HOST/test") {
            setJsonBody(
                SignUpRequestBody(accountType = requestOption.accountType.name)
            )
        }.body()
    }
}