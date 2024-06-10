package com.prography.gwasuwon

import NavigationEvent
import com.prography.domain.account.SocialLoginEvent
import com.prography.domain.account.model.SocialLoginType
import com.prography.domain.account.repository.AccountRepository
import com.prography.domain.account.usecase.SignInUseCase
import com.prography.domain.configuration.ConfigurationEvent
import com.prography.domain.preference.ThemePreferenceImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow

/**
 * Created by MyeongKi.
 */
object AppContainer {
    val configurationEvent: MutableSharedFlow<ConfigurationEvent> = MutableSharedFlow()
    val navigateEventFlow: MutableSharedFlow<NavigationEvent> = MutableSharedFlow()
    val socialLoginEventFlow: MutableSharedFlow<SocialLoginEvent> = MutableSharedFlow()

    private val accountRepository by lazy {
        object : AccountRepository{
            override fun signIn(type: SocialLoginType, accessKey: String): Flow<Unit> {
                return flow {  }
            }

        }
    }
    val themePreference by lazy {
        ThemePreferenceImpl(GwasuwonApplication.currentApplication)
    }
    val signInUseCase by lazy {
        SignInUseCase(accountRepository)
    }
}