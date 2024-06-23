package com.prography.lesson

import NavigationEvent
import androidx.paging.PagingData
import com.prography.domain.lesson.model.Lesson
import com.prography.domain.lesson.usecase.LoadLessonsUseCase
import com.prography.usm.holder.UiStateMachine
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach

/**
 * Created by MyeongKi.
 */
class LessonsUiMachine(
    coroutineScope: CoroutineScope,
    navigateFlow: MutableSharedFlow<NavigationEvent>,
    loadLessonsUseCase: LoadLessonsUseCase,
) : UiStateMachine<LessonsUiState, LessonsMachineState, LessonsActionEvent, LessonsIntent>(
    coroutineScope,
) {
    val photosPagingFlow: Flow<PagingData<Lesson>> = loadLessonsUseCase()

    override var machineInternalState: LessonsMachineState = LessonsMachineState

    private val navigateLessonDetailFlow = actionFlow
        .filterIsInstance<LessonsActionEvent.NavigateLessonDetail>()
        .onEach {
            navigateFlow.emit(NavigationEvent.NavigateLessonsRoute)
        }
    private val navigateCreateLessonFlow = actionFlow
        .filterIsInstance<LessonsActionEvent.NavigateCreateLesson>()
        .onEach {
            navigateFlow.emit(NavigationEvent.NavigateLessonsRoute)
        }
    override val outerNotifyScenarioActionFlow = merge(
        navigateLessonDetailFlow,
        navigateCreateLessonFlow
    )

    init {
        initMachine()
    }

    override fun mergeStateChangeScenarioActionsFlow(): Flow<LessonsMachineState> {
        return flow {
            emit(LessonsMachineState)
        }
    }
}
