package com.prography.network.account

import com.prography.domain.account.request.SignInRequestOption
import com.prography.domain.account.request.SignUpRequestOption
import com.prography.network.GWASUWON_HOST
import com.prography.network.account.body.SignInRequestBody
import com.prography.network.account.body.SignUpRequestBody
import com.prography.network.account.response.SignInResponse
import com.prography.network.account.response.SignUpResponse
import com.prography.network.setJsonBody
import com.prography.utils.network.NetworkHelper
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post

/**
 * Created by MyeongKi.
 */
class AccountHttpClient(
    private val httpClient: () -> HttpClient,
    private val networkHelper: NetworkHelper
) {
    suspend fun requestSignIn(requestOption: SignInRequestOption): SignInResponse {
        networkHelper.checkNetworkAvailable()
        return httpClient().post("$GWASUWON_HOST/api/v1/auth/login/${requestOption.type.name.lowercase()}") {
            setJsonBody(
                SignInRequestBody(accessToken = requestOption.accessKey)
            )
        }.body()
    }

    suspend fun requestSignUp(requestOption: SignUpRequestOption): SignUpResponse {
        networkHelper.checkNetworkAvailable()
        return httpClient().post("$GWASUWON_HOST/api/v1/users/activation") {
            setJsonBody(
                SignUpRequestBody(
                    role = requestOption.roleType.name,
                    privacyPolicyAgreement = requestOption.privacyPolicyAgreement,
                    termsOfServiceAgreement = requestOption.termsOfServiceAgreement
                )
            )
        }.body()
    }
}