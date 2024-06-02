package com.prography.network.account

import android.os.Build.HOST
import com.prography.domain.account.request.SignInRequestOption
import com.prography.network.account.body.SignInRequestBody
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
        return httpClient.post("$HOST/test") {
            setJsonBody(
                //FIXME
                SignInRequestBody(type = requestOption.type.name, accessKey = requestOption.accessKey)
            )
        }.body()
    }
}