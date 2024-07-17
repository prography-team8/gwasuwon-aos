package com.prography.qr

import NavigationEvent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.prography.domain.lesson.usecase.ParticipateLessonUseCase
import com.prography.domain.qr.CommonQrEvent
import kotlinx.coroutines.flow.MutableSharedFlow

/**
 * Created by MyeongKi.
 */
class LessonInvitedViewModel(
    navigateFlow: MutableSharedFlow<NavigationEvent>,
    commonQrFlow: MutableSharedFlow<CommonQrEvent>,
    participateLessonUseCase: ParticipateLessonUseCase
) : ViewModel() {
    val machine = LessonInvitedUiMachine(
        coroutineScope = viewModelScope,
        navigateFlow = navigateFlow,
        commonQrFlow = commonQrFlow,
        participateLessonUseCase = participateLessonUseCase,
    )

    companion object {
        fun provideFactory(
            navigateFlow: MutableSharedFlow<NavigationEvent>,
            commonQrFlow: MutableSharedFlow<CommonQrEvent>,
            participateLessonUseCase: ParticipateLessonUseCase
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return LessonInvitedViewModel(
                    navigateFlow,
                    commonQrFlow,
                    participateLessonUseCase
                ) as T
            }
        }
    }
}