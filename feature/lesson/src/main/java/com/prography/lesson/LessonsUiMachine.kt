package com.prography.lesson

import NavigationEvent
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.prography.domain.lesson.usecase.LoadLessonsUseCase
import com.prography.usm.holder.UiStateMachine
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
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
    val lessonsPagingFlow: Flow<PagingData<LessonItemUiMachine>> = loadLessonsUseCase()
        .distinctUntilChanged()
        .map { pagingData ->
            pagingData.map {
                LessonItemUiMachine(
                    lesson = it,
                    coroutineScope = coroutineScope,
                    navigateFlow = navigateFlow,
                )
            }
        }
        .cachedIn(coroutineScope)

    override var machineInternalState: LessonsMachineState = LessonsMachineState()

    private val requestRefreshFlow = actionFlow
        .filterIsInstance<LessonsActionEvent.RequestRefresh>()
        .map {
            machineInternalState.copy(
                isRequestRefresh = true
            )
        }
    private val onStartRefreshFlow = actionFlow
        .filterIsInstance<LessonsActionEvent.OnStartRefresh>()
        .map {
            machineInternalState.copy(
                isRequestRefresh = false
            )
        }

    private val navigateCreateLessonFlow = actionFlow
        .filterIsInstance<LessonsActionEvent.NavigateCreateLesson>()
        .onEach {
            navigateFlow.emit(NavigationEvent.NavigateCreateLessonRoute)
        }

    override val outerNotifyScenarioActionFlow = merge(
        navigateCreateLessonFlow
    )

    init {
        initMachine()
    }

    override fun mergeStateChangeScenarioActionsFlow(): Flow<LessonsMachineState> {
        return merge(
            requestRefreshFlow,
            onStartRefreshFlow,
        )
    }
}
