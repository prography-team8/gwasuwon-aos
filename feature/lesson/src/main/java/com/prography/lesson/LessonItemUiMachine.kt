package com.prography.lesson

import NavigationEvent
import com.prography.domain.lesson.model.Lesson
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
    lesson: Lesson,
    coroutineScope: CoroutineScope,
    navigateFlow: MutableSharedFlow<NavigationEvent>,
) : UiStateMachine<LessonItemUiState, LessonItemMachineState, LessonItemActionEvent, LessonItemIntent>(
    coroutineScope,
) {

    override var machineInternalState: LessonItemMachineState = LessonItemMachineState(lesson = lesson)

    private val navigateLessonDetailFlow = actionFlow
        .filterIsInstance<LessonItemActionEvent.NavigateManagingLesson>()
        .onEach {
            navigateFlow.emit(NavigationEvent.NavigateManagingLessonRoute(lessonId = machineInternalState.lesson.lessonId))
        }
    override val outerNotifyScenarioActionFlow = merge(
        navigateLessonDetailFlow
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
