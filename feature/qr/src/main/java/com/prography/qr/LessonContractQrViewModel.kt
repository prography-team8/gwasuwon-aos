package com.prography.qr

import NavigationEvent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.prography.domain.lesson.usecase.LoadLessonContractUrlUseCase
import com.prography.utils.clipboar.ClipboardHelper
import kotlinx.coroutines.flow.MutableSharedFlow

/**
 * Created by MyeongKi.
 */
class LessonContractQrViewModel(
    lessonId: Long,
    navigateFlow: MutableSharedFlow<NavigationEvent>,
    clipboardHelper: ClipboardHelper,
    loadContractUrlUseCase: LoadLessonContractUrlUseCase
) : ViewModel() {
    val machine = LessonContractQrUiMachine(
        coroutineScope = viewModelScope,
        lessonId = lessonId,
        navigateFlow = navigateFlow,
        clipboardHelper = clipboardHelper,
        loadContractUrlUseCase = loadContractUrlUseCase,
    )

    companion object {
        fun provideFactory(
            lessonId: Long,
            navigateFlow: MutableSharedFlow<NavigationEvent>,
            clipboardHelper: ClipboardHelper,
            loadContractUrlUseCase: LoadLessonContractUrlUseCase
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return LessonContractQrViewModel(
                    lessonId,
                    navigateFlow,
                    clipboardHelper,
                    loadContractUrlUseCase
                ) as T
            }
        }
    }
}