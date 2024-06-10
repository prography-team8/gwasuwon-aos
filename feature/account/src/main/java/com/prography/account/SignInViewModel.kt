package com.prography.account

import NavigationEvent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.prography.account.kakao.KakaoLoginManager
import com.prography.domain.account.SocialLoginEvent
import com.prography.domain.account.usecase.SignInUseCase
import kotlinx.coroutines.flow.MutableSharedFlow

/**
 * Created by MyeongKi.
 */
class SignInViewModel(
    navigateFlow: MutableSharedFlow<NavigationEvent>,
    accountOuterFlow: MutableSharedFlow<SocialLoginEvent>,
    signInUseCase: SignInUseCase
) : ViewModel() {
    val machine = SignInUiMachine(
        coroutineScope = viewModelScope,
        navigateFlow = navigateFlow,
        signInUseCase = signInUseCase,
        socialLoginFlow = accountOuterFlow,
    )

    companion object {
        fun provideFactory(
            navigateFlow: MutableSharedFlow<NavigationEvent>,
            signInUseCase: SignInUseCase,
            accountOuterFlow: MutableSharedFlow<SocialLoginEvent>,

            ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return SignInViewModel(
                    navigateFlow,
                    accountOuterFlow,
                    signInUseCase,
                ) as T
            }
        }
    }
}