package com.prography.qr

import NavigationEvent
import com.prography.domain.lesson.usecase.LoadLessonContractUrlUseCase
import com.prography.qr.domain.GenerateLessonContractUrlQrUseCase
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
class LessonContractQrUiMachine(
    coroutineScope: CoroutineScope,
    lessonId: Long,
    navigateFlow: MutableSharedFlow<NavigationEvent>,
    loadContractUrlUseCase: LoadLessonContractUrlUseCase
) : UiStateMachine<
        LessonContractQrUiState,
        LessonContractQrMachineState,
        LessonContractQrActionEvent,
        LessonContractQrIntent>(coroutineScope) {
    private val generateLessonContractUrlQrUseCase = GenerateLessonContractUrlQrUseCase(
        generateQrUseCase = GenerateQrUseCase(),
        loadContractUrl = loadContractUrlUseCase
    )
    override var machineInternalState: LessonContractQrMachineState = LessonContractQrMachineState()

    private val popBackFlow = actionFlow
        .filterIsInstance<LessonContractQrActionEvent.PopBack>()
        .onEach {
            navigateFlow.emit(NavigationEvent.PopBack)
        }
    private val generateQrFlow = actionFlow
        .filterIsInstance<LessonContractQrActionEvent.GenerateQr>()
        .transform { emitAll(generateLessonContractUrlQrUseCase(lessonId).asResult()) }
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
        popBackFlow
    )

    init {
        initMachine()
    }

    override fun mergeStateChangeScenarioActionsFlow(): Flow<LessonContractQrMachineState> {
        return generateQrFlow
    }
}
