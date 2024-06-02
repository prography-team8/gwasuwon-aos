package com.prography.gwasuwon

import NavigationEvent
import com.prography.domain.account.SocialLoginEvent
import com.prography.domain.account.exception.NotFoundAccountException
import com.prography.domain.account.model.AccountInfo
import com.prography.domain.account.repository.AccountRepository
import com.prography.domain.account.request.SignInRequestOption
import com.prography.domain.account.usecase.SignInUseCase
import com.prography.domain.configuration.ConfigurationEvent
import com.prography.domain.preference.ThemePreferenceImpl
import com.prography.utils.security.GwasuwonCryptoHelper
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
    private val gwasuwonCryptoHelper by lazy {
        GwasuwonCryptoHelper(GwasuwonApplication.currentApplication)
    }
    private val accountRepository by lazy {
        object : AccountRepository {
            override fun signIn(requestOption: SignInRequestOption): Flow<AccountInfo> {
                return flow {
                    throw NotFoundAccountException()
                }
            }

        }
    }
    val themePreference by lazy {
        ThemePreferenceImpl(GwasuwonApplication.currentApplication)
    }
    val signInUseCase by lazy {
        SignInUseCase(
            accountRepository,
            gwasuwonCryptoHelper
        )
    }
}