package com.prography.qr

import NavigationEvent
import com.prography.qr.domain.GenerateInviteStudentQrUseCase
import com.prography.qr.domain.GenerateGwasuwonQrUseCase
import com.prography.qr.domain.GenerateQrUseCase
import com.prography.usm.holder.UiStateMachine
import com.prography.usm.result.Result
import com.prography.usm.result.asResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.transform

/**
 * Created by MyeongKi.
 */
class InviteStudentQrUiMachine(
    coroutineScope: CoroutineScope,
    lessonId: Long,
    navigateFlow: MutableSharedFlow<NavigationEvent>
) : UiStateMachine<
        OnlyQrUiState,
        OnlyQrMachineState,
        InviteStudentQrActionEvent,
        InviteStudentQrIntent>(coroutineScope) {
    private val generateInviteStudentQrUseCase = GenerateInviteStudentQrUseCase(
        generateGwasuwonQrUseCase = GenerateGwasuwonQrUseCase(
            generateQrUseCase = GenerateQrUseCase()
        )
    )
    override var machineInternalState: OnlyQrMachineState = OnlyQrMachineState()

    private val navigateHomeFlow = actionFlow
        .filterIsInstance<InviteStudentQrActionEvent.NavigateHome>()
        .onEach {
            navigateFlow.emit(NavigationEvent.NavigateLessonsRoute)
        }
    private val popBackFlow = actionFlow
        .filterIsInstance<InviteStudentQrActionEvent.PopBack>()
        .onEach {
            navigateFlow.emit(NavigationEvent.PopBack)
        }
    private val generateQrFlow = actionFlow
        .filterIsInstance<InviteStudentQrActionEvent.GenerateQr>()
        .transform { emitAll(generateInviteStudentQrUseCase(lessonId).asResult()) }
        .map {
            when (it) {
                is Result.Error -> {
                    machineInternalState
                }

                is Result.Loading -> {
                    machineInternalState
                }

                is Result.Success -> {
                    machineInternalState.copy(qr = it.data)
                }
            }
        }
    override val outerNotifyScenarioActionFlow = merge(
        navigateHomeFlow,
        popBackFlow
    )

    init {
        initMachine()
    }

    override fun mergeStateChangeScenarioActionsFlow(): Flow<OnlyQrMachineState> {
        return generateQrFlow
    }
}
