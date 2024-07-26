package com.prography.lesson

import NavigationEvent
import com.prography.domain.lesson.model.LessonCard
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
class LessonItemUiMachine(
    lesson: LessonCard,
    coroutineScope: CoroutineScope,
    navigateFlow: MutableSharedFlow<NavigationEvent>,
) : UiStateMachine<LessonItemUiState, LessonItemMachineState, LessonItemActionEvent, LessonItemIntent>(
    coroutineScope,
) {

    override var machineInternalState: LessonItemMachineState = LessonItemMachineState(lesson = lesson)

    private val navigateLessonDetailFlow = actionFlow
        .filterIsInstance<LessonItemActionEvent.NavigateLessonDetail>()
        .onEach {
            navigateFlow.emit(NavigationEvent.NavigateLessonDetailRoute(lessonId = machineInternalState.lesson.id))
        }
    private val navigateExtensionRequiredFlow = actionFlow
        .filterIsInstance<LessonItemActionEvent.NavigateExtensionLesson>()
        .onEach {
            navigateFlow.emit(NavigationEvent.NavigateExtensionLessonRoute(lessonId = machineInternalState.lesson.id))
        }
    override val outerNotifyScenarioActionFlow = merge(
        navigateLessonDetailFlow,
        navigateExtensionRequiredFlow
    )

    init {
        initMachine()
    }

    override fun mergeStateChangeScenarioActionsFlow(): Flow<LessonItemMachineState> {
        return flow {
            emit(machineInternalState)
        }
    }
}
