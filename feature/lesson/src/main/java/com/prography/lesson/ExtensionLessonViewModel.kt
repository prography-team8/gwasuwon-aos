package com.prography.lesson

import NavigationEvent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.prography.domain.lesson.CommonLessonEvent
import com.prography.domain.lesson.usecase.LoadLessonInfoDetailUseCase
import com.prography.domain.lesson.usecase.UpdateLessonUseCase
import kotlinx.coroutines.flow.MutableSharedFlow

/**
 * Created by MyeongKi.
 */
class ExtensionLessonViewModel(
    lessonId: Long,
    navigateFlow: MutableSharedFlow<NavigationEvent>,
    commonLessonEvent: MutableSharedFlow<CommonLessonEvent>,
    updateLessonUseCase: UpdateLessonUseCase,
    loadLessonInfoDetailUseCase: LoadLessonInfoDetailUseCase,
) : ViewModel() {
    val machine = ExtensionLessonUiMachine(
        lessonId = lessonId,
        commonLessonEvent = commonLessonEvent,
        coroutineScope = viewModelScope,
        navigateFlow = navigateFlow,
        updateLessonUseCase = updateLessonUseCase,
        loadLessonInfoDetailUseCase = loadLessonInfoDetailUseCase,
    )

    companion object {
        fun provideFactory(
            lessonId: Long,
            navigateFlow: MutableSharedFlow<NavigationEvent>,
            commonLessonEvent: MutableSharedFlow<CommonLessonEvent>,
            updateLessonUseCase: UpdateLessonUseCase,
            loadLessonInfoDetailUseCase: LoadLessonInfoDetailUseCase,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ExtensionLessonViewModel(
                    lessonId, navigateFlow, commonLessonEvent, updateLessonUseCase, loadLessonInfoDetailUseCase
                ) as T
            }
        }
    }
}