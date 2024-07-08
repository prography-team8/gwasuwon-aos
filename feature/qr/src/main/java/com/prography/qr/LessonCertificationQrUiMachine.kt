package com.prography.qr

import NavigationEvent
import com.prography.qr.domain.GenerateGwasuwonQrUseCase
import com.prography.qr.domain.GenerateLessonCertificationQrUseCase
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
class LessonCertificationQrUiMachine(
    coroutineScope: CoroutineScope,
    lessonId: Long,
    navigateFlow: MutableSharedFlow<NavigationEvent>
) : UiStateMachine<
        OnlyQrUiState,
        OnlyQrMachineState,
        LessonCertificationQrActionEvent,
        LessonCertificationQrIntent>(coroutineScope) {
    private val generateLessonCertificationQrUseCase = GenerateLessonCertificationQrUseCase(
        generateGwasuwonQrUseCase = GenerateGwasuwonQrUseCase(
            generateQrUseCase = GenerateQrUseCase()
        )
    )
    override var machineInternalState: OnlyQrMachineState = OnlyQrMachineState()

    private val popBackFlow = actionFlow
        .filterIsInstance<LessonCertificationQrActionEvent.PopBack>()
        .onEach {
            navigateFlow.emit(NavigationEvent.PopBack)
        }
    private val generateQrFlow = actionFlow
        .filterIsInstance<LessonCertificationQrActionEvent.GenerateQr>()
        .transform { emitAll(generateLessonCertificationQrUseCase(lessonId).asResult()) }
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

    override fun mergeStateChangeScenarioActionsFlow(): Flow<OnlyQrMachineState> {
        return generateQrFlow
    }
}
