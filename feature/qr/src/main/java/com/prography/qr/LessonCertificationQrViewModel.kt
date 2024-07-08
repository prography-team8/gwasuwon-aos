package com.prography.qr

import NavigationEvent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow

/**
 * Created by MyeongKi.
 */
class LessonCertificationQrViewModel(
    lessonId: Long,
    navigateFlow: MutableSharedFlow<NavigationEvent>,
) : ViewModel() {
    val machine = LessonCertificationQrUiMachine(
        coroutineScope = viewModelScope,
        lessonId = lessonId,
        navigateFlow = navigateFlow,
    )

    companion object {
        fun provideFactory(
            lessonId: Long,
            navigateFlow: MutableSharedFlow<NavigationEvent>,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return LessonCertificationQrViewModel(
                    lessonId,
                    navigateFlow,
                ) as T
            }
        }
    }
}