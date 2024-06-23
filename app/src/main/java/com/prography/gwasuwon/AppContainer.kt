package com.prography.gwasuwon

import NavigationEvent
import com.prography.account.AccountInfoManagerImpl
import com.prography.domain.account.AccountInfoManager
import com.prography.domain.account.SocialLoginEvent
import com.prography.domain.account.repository.AccountRepositoryImpl
import com.prography.domain.account.usecase.SignInUseCase
import com.prography.domain.configuration.ConfigurationEvent
import com.prography.domain.preference.AccountPreferenceImpl
import com.prography.domain.preference.ThemePreferenceImpl
import com.prography.network.HttpClientFactory
import com.prography.network.account.AccountHttpClient
import com.prography.network.account.AccountRemoteDataSource
import com.prography.utils.security.GwasuwonAccessTokenHelper
import com.prography.utils.security.GwasuwonRefreshTokenHelper
import kotlinx.coroutines.flow.MutableSharedFlow

/**
 * Created by MyeongKi.
 */
object AppContainer {
    val configurationEvent: MutableSharedFlow<ConfigurationEvent> = MutableSharedFlow()
    val navigateEventFlow: MutableSharedFlow<NavigationEvent> = MutableSharedFlow()
    val socialLoginEventFlow: MutableSharedFlow<SocialLoginEvent> = MutableSharedFlow()
    private val gwasuwonAccessTokenHelper by lazy {
        GwasuwonAccessTokenHelper(GwasuwonApplication.currentApplication)
    }
    private val gwasuwonRefreshTokenHelper by lazy {
        GwasuwonRefreshTokenHelper(GwasuwonApplication.currentApplication)
    }
    private val accountPreference by lazy {
        AccountPreferenceImpl(GwasuwonApplication.currentApplication)
    }
    val accountInfoManager: AccountInfoManager by lazy {
        AccountInfoManagerImpl.apply {
            init(
                accessTokenHelper = gwasuwonAccessTokenHelper,
                refreshTokenHelper = gwasuwonRefreshTokenHelper,
                accountPreference = accountPreference
            )
        }
    }
    private val gwasuwonHttpClient by lazy {
        HttpClientFactory.createGwasuwonHttpClient(accountInfoManager)
    }
    private val accountRepository by lazy {
        AccountRepositoryImpl(
            remoteDataSource = AccountRemoteDataSource(
                httpClient = AccountHttpClient(
                    httpClient = gwasuwonHttpClient
                )
            )
        )
    }
    val themePreference by lazy {
        ThemePreferenceImpl(GwasuwonApplication.currentApplication)
    }
    val signInUseCase by lazy {
        SignInUseCase(
            accountRepository,
            accountInfoManager
        )
    }
}