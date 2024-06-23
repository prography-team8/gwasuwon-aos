package com.prography.lesson

import NavigationEvent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.prography.domain.lesson.usecase.LoadLessonsUseCase
import kotlinx.coroutines.flow.MutableSharedFlow

/**
 * Created by MyeongKi.
 */
class LessonsViewModel(
    navigateFlow: MutableSharedFlow<NavigationEvent>,
    loadLessonsUseCase: LoadLessonsUseCase,
) : ViewModel() {
    val machine = LessonsUiMachine(
        coroutineScope = viewModelScope,
        navigateFlow = navigateFlow,
        loadLessonsUseCase = loadLessonsUseCase,
    )

    companion object {
        fun provideFactory(
            navigateFlow: MutableSharedFlow<NavigationEvent>,
            loadLessonsUseCase: LoadLessonsUseCase,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return LessonsViewModel(
                    navigateFlow,
                    loadLessonsUseCase,
                ) as T
            }
        }
    }
}