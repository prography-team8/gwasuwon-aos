package com.prography.lesson

import NavigationEvent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.prography.domain.lesson.CommonLessonEvent
import com.prography.domain.lesson.usecase.DeleteLessonUseCase
import com.prography.domain.lesson.usecase.LoadLessonDatesUseCase
import com.prography.domain.lesson.usecase.LoadLessonUseCase
import kotlinx.coroutines.flow.MutableSharedFlow

/**
 * Created by MyeongKi.
 */
class LessonDetailViewModel(
    lessonId: Long,
    navigateFlow: MutableSharedFlow<NavigationEvent>,
    commonLessonEvent: MutableSharedFlow<CommonLessonEvent>,
    loadLessonUseCase: LoadLessonUseCase,
    loadLessonDatesUseCase: LoadLessonDatesUseCase,
    deleteLessonUseCase: DeleteLessonUseCase,
) : ViewModel() {
    val machine = LessonDetailUiMachine(
        lessonId = lessonId,
        coroutineScope = viewModelScope,
        navigateFlow = navigateFlow,
        commonLessonEvent = commonLessonEvent,
        loadLessonUseCase = loadLessonUseCase,
        loadLessonDatesUseCase = loadLessonDatesUseCase,
        deleteLessonUseCase = deleteLessonUseCase
    )

    companion object {
        fun provideFactory(
            lessonId: Long,
            navigateFlow: MutableSharedFlow<NavigationEvent>,
            commonLessonEvent: MutableSharedFlow<CommonLessonEvent>,
            loadLessonUseCase: LoadLessonUseCase,
            loadLessonDatesUseCase: LoadLessonDatesUseCase,
            deleteLessonUseCase: DeleteLessonUseCase,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return LessonDetailViewModel(
                    lessonId,
                    navigateFlow,
                    commonLessonEvent,
                    loadLessonUseCase,
                    loadLessonDatesUseCase,
                    deleteLessonUseCase
                ) as T
            }
        }
    }
}