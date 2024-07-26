package com.prography.qr

import NavigationEvent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.prography.domain.lesson.usecase.JoinLessonUseCase
import com.prography.domain.preference.AccountPreference
import com.prography.domain.qr.CommonQrEvent
import kotlinx.coroutines.flow.MutableSharedFlow

/**
 * Created by MyeongKi.
 */
class LessonInvitedViewModel(
    navigateFlow: MutableSharedFlow<NavigationEvent>,
    commonQrFlow: MutableSharedFlow<CommonQrEvent>,
    joinLessonUseCase: JoinLessonUseCase,
    accountPreference: AccountPreference
) : ViewModel() {
    val machine = LessonInvitedUiMachine(
        coroutineScope = viewModelScope,
        navigateFlow = navigateFlow,
        commonQrFlow = commonQrFlow,
        joinLessonUseCase = joinLessonUseCase,
        accountPreference = accountPreference
    )

    companion object {
        fun provideFactory(
            navigateFlow: MutableSharedFlow<NavigationEvent>,
            commonQrFlow: MutableSharedFlow<CommonQrEvent>,
            joinLessonUseCase: JoinLessonUseCase,
            accountPreference: AccountPreference
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return LessonInvitedViewModel(
                    navigateFlow,
                    commonQrFlow,
                    joinLessonUseCase,
                    accountPreference
                ) as T
            }
        }
    }
}