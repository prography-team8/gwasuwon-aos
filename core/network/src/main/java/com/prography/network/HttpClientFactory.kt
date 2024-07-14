package com.prography.network

import com.prography.domain.account.AccountInfoManager
import com.prography.network.account.RefreshDelegate
import io.ktor.client.HttpClient
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * Created by MyeongKi.
 */
object HttpClientFactory {
    fun createGwasuwonHttpClient(
        accountInfoManager: AccountInfoManager,
        navigateSignInRoute: suspend () -> Unit
    ): HttpClient = HttpClient {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    coerceInputValues = true
                }
            )
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 3000
        }
        install(Auth) {
            bearer {
                loadTokens {
                    BearerTokens(
                        accessToken = accountInfoManager.getAccountInfo()?.accessToken ?: "", // 초기 액세스 토큰
                        refreshToken = accountInfoManager.getAccountInfo()?.refreshToken ?: ""
                    )
                }
                refreshTokens {
                    RefreshDelegate(client, accountInfoManager, navigateSignInRoute).invoke()
                }
            }
        }
        HttpResponseValidator {
            validateResponse { response ->
                when (val statusCode = response.status.value) {
                    in 400..499 -> {
                        throw ClientRequestException(response, "Client error with status code $statusCode")
                    }

                    in 500..599 -> {
                        throw ServerResponseException(response, "Server error with status code $statusCode")
                    }

                    else -> Unit
                }
            }
        }
    }
}