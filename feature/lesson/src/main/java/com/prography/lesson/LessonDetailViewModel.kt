package com.prography.lesson

import NavigationEvent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.prography.domain.lesson.usecase.LoadLessonDatesUseCase
import com.prography.domain.lesson.usecase.LoadLessonUseCase
import kotlinx.coroutines.flow.MutableSharedFlow

/**
 * Created by MyeongKi.
 */
class LessonDetailViewModel(
    lessonId: Long,
    navigateFlow: MutableSharedFlow<NavigationEvent>,
    loadLessonUseCase: LoadLessonUseCase,
    loadLessonDatesUseCase: LoadLessonDatesUseCase
) : ViewModel() {
    val machine = LessonDetailUiMachine(
        lessonId = lessonId,
        coroutineScope = viewModelScope,
        navigateFlow = navigateFlow,
        loadLessonUseCase = loadLessonUseCase,
        loadLessonDatesUseCase = loadLessonDatesUseCase,
    )

    companion object {
        fun provideFactory(
            lessonId: Long,
            navigateFlow: MutableSharedFlow<NavigationEvent>,
            loadLessonUseCase: LoadLessonUseCase,
            loadLessonDatesUseCase: LoadLessonDatesUseCase
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return LessonDetailViewModel(
                    lessonId,
                    navigateFlow,
                    loadLessonUseCase,
                    loadLessonDatesUseCase
                ) as T
            }
        }
    }
}