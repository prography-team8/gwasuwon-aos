package com.prography.usm_sample

import com.prography.domain.usecase.SaveCurrentCountUseCase
import com.prography.usm.holder.UiStateMachine
import com.prography.usm.result.Result
import com.prography.usm.result.asResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.transform

/**
 * Created by MyeongKi.
 */
class SampleCountUiMachine(
    coroutineScope: CoroutineScope,
    saveCurrentCountUseCase: SaveCurrentCountUseCase,
) : UiStateMachine<SampleCountUiState, SampleCountMachineState, SampleCountActionEvent, SampleCountIntent>(coroutineScope) {
    override var machineInternalState: SampleCountMachineState = SampleCountMachineState(isLoading = false, count = 0)
    private val addCountActionFlow = actionFlow
        .filterIsInstance<SampleCountActionEvent.AddCount>()
        .transform {
            emitAll(saveCurrentCountUseCase(machineInternalState.count.inc()).asResult())
        }
        .map {
            when (it) {
                is Result.Error -> {
                    machineInternalState.copy(isLoading = false, count = 0)
                }

                is Result.Loading -> {
                    machineInternalState.copy(isLoading = true)
                }

                is Result.Success -> {
                    machineInternalState.copy(isLoading = false, count = it.data)
                }
            }
        }

    override fun mergeScenarioActionsFlow(): Flow<SampleCountMachineState> = merge(addCountActionFlow)
}