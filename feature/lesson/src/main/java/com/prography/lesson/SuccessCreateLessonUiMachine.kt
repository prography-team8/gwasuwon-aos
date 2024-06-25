package com.prography.lesson

import NavigationEvent
import com.prography.domain.lesson.CommonLessonEvent
import com.prography.domain.lesson.request.CreateLessonRequestOption
import com.prography.domain.lesson.usecase.CreateLessonUseCase
import com.prography.usm.holder.UiStateMachine
import com.prography.usm.result.Result
import com.prography.usm.result.asResult
import kotlinx.collections.immutable.toImmutableSet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.transform

/**
 * Created by MyeongKi.
 */
class SuccessCreateLessonUiMachine(
    coroutineScope: CoroutineScope,
    lessonId:Long,
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
            //FIXME
        }
    private val navigateInviteStudentQrFlow = actionFlow
        .filterIsInstance<SuccessCreateLessonActionEvent.NavigateInviteStudentQr>()
        .onEach {
            //FIXME
        }
    private val navigateHomeFlow = actionFlow
        .filterIsInstance<SuccessCreateLessonActionEvent.NavigateHome>()
        .onEach {
            //FIXME
        }

    override val outerNotifyScenarioActionFlow = merge(
        navigateLessonContractQrFlow,
        navigateInviteStudentQrFlow,
        navigateHomeFlow,
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
