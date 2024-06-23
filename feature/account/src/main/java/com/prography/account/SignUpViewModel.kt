package com.prography.account

import NavigationEvent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.prography.domain.account.usecase.SignUpUseCase
import kotlinx.coroutines.flow.MutableSharedFlow

/**
 * Created by MyeongKi.
 */
class SignUpViewModel(
    navigateFlow: MutableSharedFlow<NavigationEvent>,
    signUpUseCase: SignUpUseCase
) : ViewModel() {
    val machine = SignUpUiMachine(
        coroutineScope = viewModelScope,
        navigateFlow = navigateFlow,
        signUpUseCase = signUpUseCase,
    )

    companion object {
        fun provideFactory(
            navigateFlow: MutableSharedFlow<NavigationEvent>,
            signUpUseCase: SignUpUseCase
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return SignUpViewModel(
                    navigateFlow,
                    signUpUseCase,
                ) as T
            }
        }
    }
}