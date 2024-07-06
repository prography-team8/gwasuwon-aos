package com.prography.lesson

import NavigationEvent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.prography.domain.lesson.CommonLessonEvent
import com.prography.domain.lesson.usecase.LoadLessonUseCase
import com.prography.domain.lesson.usecase.UpdateLessonUseCase
import kotlinx.coroutines.flow.MutableSharedFlow

/**
 * Created by MyeongKi.
 */
class LessonInfoDetailViewModel(
    lessonId: Long,
    navigateFlow: MutableSharedFlow<NavigationEvent>,
    commonLessonEvent: MutableSharedFlow<CommonLessonEvent>,
    loadLessonUseCase: LoadLessonUseCase,
    updateLessonUseCase: UpdateLessonUseCase
) : ViewModel() {
    val machine = LessonInfoDetailUiMachine(
        lessonId = lessonId,
        commonLessonEvent = commonLessonEvent,
        coroutineScope = viewModelScope,
        navigateFlow = navigateFlow,
        loadLessonUseCase = loadLessonUseCase,
        updateLessonUseCase = updateLessonUseCase,
    )

    companion object {
        fun provideFactory(
            lessonId: Long,
            navigateFlow: MutableSharedFlow<NavigationEvent>,
            commonLessonEvent: MutableSharedFlow<CommonLessonEvent>,
            loadLessonUseCase: LoadLessonUseCase,
            updateLessonUseCase: UpdateLessonUseCase
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return LessonInfoDetailViewModel(
                    lessonId,
                    navigateFlow,
                    commonLessonEvent,
                    loadLessonUseCase,
                    updateLessonUseCase
                ) as T
            }
        }
    }
}