package com.prography.qr

import NavigationEvent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.prography.domain.lesson.usecase.LoadLessonContractUrlUseCase
import kotlinx.coroutines.flow.MutableSharedFlow

/**
 * Created by MyeongKi.
 */
class LessonContractQrViewModel(
    lessonId: Long,
    navigateFlow: MutableSharedFlow<NavigationEvent>,
    loadContractUrlUseCase: LoadLessonContractUrlUseCase
) : ViewModel() {
    val machine = LessonContractQrUiMachine(
        coroutineScope = viewModelScope,
        lessonId = lessonId,
        navigateFlow = navigateFlow,
        loadContractUrlUseCase = loadContractUrlUseCase,
    )

    companion object {
        fun provideFactory(
            lessonId: Long,
            navigateFlow: MutableSharedFlow<NavigationEvent>,
            loadContractUrlUseCase: LoadLessonContractUrlUseCase
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return LessonContractQrViewModel(
                    lessonId,
                    navigateFlow,
                    loadContractUrlUseCase
                ) as T
            }
        }
    }
}