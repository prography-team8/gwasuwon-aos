package com.prography.lesson

import NavigationEvent
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
class SuccessCreateLessonUiMachine(
    coroutineScope: CoroutineScope,
    lessonId: Long,
    navigateFlow: MutableSharedFlow<NavigationEvent>
) : UiStateMachine<
        SuccessCreateLessonUiState,
        SuccessCreateLessonMachineState,
        SuccessCreateLessonActionEvent,
        SuccessCreateLessonIntent>(coroutineScope) {
    override var machineInternalState: SuccessCreateLessonMachineState = SuccessCreateLessonMachineState

    private val navigateLessonContractQrFlow = actionFlow
        .filterIsInstance<SuccessCreateLessonActionEvent.NavigateLessonContractQr>()
        .onEach {
            navigateFlow.emit(NavigationEvent.NavigateLessonContractQrRoute(lessonId = lessonId))
        }
    private val navigateInviteStudentQrFlow = actionFlow
        .filterIsInstance<SuccessCreateLessonActionEvent.NavigateInviteStudentQr>()
        .onEach {
            navigateFlow.emit(NavigationEvent.NavigateInviteStudentQrRoute(lessonId = lessonId))
        }
    private val navigateHomeFlow = actionFlow
        .filterIsInstance<SuccessCreateLessonActionEvent.NavigateHome>()
        .onEach {
            navigateFlow.emit(NavigationEvent.NavigateLessonsRoute)
        }
    private val navigateLessonInfoDetailFlow = actionFlow
        .filterIsInstance<SuccessCreateLessonActionEvent.NavigateLessonInfoDetail>()
        .onEach {
            navigateFlow.emit(NavigationEvent.NavigateLessonInfoDetailRoute(lessonId = lessonId))

        }
    override val outerNotifyScenarioActionFlow = merge(
        navigateLessonContractQrFlow,
        navigateInviteStudentQrFlow,
        navigateHomeFlow,
        navigateLessonInfoDetailFlow
    )

    init {
        initMachine()
    }

    override fun mergeStateChangeScenarioActionsFlow(): Flow<SuccessCreateLessonMachineState> {
        return flow {
            emit(SuccessCreateLessonMachineState)
        }
    }
}
