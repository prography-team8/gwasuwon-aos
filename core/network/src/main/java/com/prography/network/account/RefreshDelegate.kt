package com.prography.network.account

import android.util.Log
import com.prography.domain.account.AccountInfoManager
import com.prography.domain.account.model.RefreshToken
import com.prography.network.GWASUWON_HOST
import com.prography.network.account.body.RefreshRequestBody
import com.prography.network.account.response.RefreshResponse
import com.prography.network.setJsonBody
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.http.HttpHeaders

/**
 * Created by MyeongKi.
 */
class RefreshDelegate(
    private val httpClient: HttpClient,
    private val accountInfoManager: AccountInfoManager,
    private val navigateSignInRoute: suspend () -> Unit
) {
    suspend operator fun invoke(): BearerTokens? {
        val response: RefreshResponse? = try {
            val response:RefreshResponse = httpClient.post("$GWASUWON_HOST/api/v1/auth/token/refresh") {
                headers { append(HttpHeaders.Authorization, "Bearer ${accountInfoManager.getAccountInfo()?.accessToken ?: ""}") }
                setJsonBody(
                    RefreshRequestBody(
                        refreshToken = accountInfoManager.getAccountInfo()?.refreshToken ?: "",

                        )
                )
            }.body()
            if(response.refreshToken.isEmpty() || response.accessToken.isEmpty()) throw Exception("refresh token error")
            response
        } catch (e: Exception) {
            Log.e("RefreshDelegate", e.message ?: "refresh token error")
            accountInfoManager.clear()
            navigateSignInRoute()
            null
        }
        return if (response != null) {
            with(accountInfoManager) {
                updateRefreshToken(
                    RefreshToken(
                        refreshToken = response.refreshToken,
                        accessToken = response.accessToken
                    )
                )
            }
            BearerTokens(
                accessToken = response.accessToken,
                refreshToken = response.refreshToken
            )

        } else {
            accountInfoManager.clear()
            navigateSignInRoute()
            null
        }
    }
}