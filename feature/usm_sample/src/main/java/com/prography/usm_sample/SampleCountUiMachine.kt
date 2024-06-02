package com.prography.usm_sample

import NavigationEvent
import com.prography.domain.usecase.LoadLastCountUseCase
import com.prography.domain.usecase.SaveCurrentCountUseCase
import com.prography.usm.holder.UiStateMachine
import com.prography.usm.result.Result
import com.prography.usm.result.asResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.transform

/**
 * Created by MyeongKi.
 */
class SampleCountUiMachine(
    private val coroutineScope: CoroutineScope,
    navigateFlow: MutableSharedFlow<NavigationEvent>,
    saveCurrentCountUseCase: SaveCurrentCountUseCase,
    loadLastCountUseCase: LoadLastCountUseCase,
) : UiStateMachine<SampleCountUiState, SampleCountMachineState, SampleCountActionEvent, SampleCountIntent>(coroutineScope) {
    override var machineInternalState: SampleCountMachineState = SampleCountMachineState(isLoading = false, count = 0)
    private val refreshActionFlow = actionFlow
        .filterIsInstance<SampleCountActionEvent.Refresh>()
        .transform {
            emitAll(loadLastCountUseCase().asResult())
        }
        .map {
            when (it) {
                is Result.Error -> {
                    machineInternalState.copy(isLoading = false, count = 0)
                }

                is Result.Loading -> {
                    machineInternalState.copy(isLoading = true)
                }

                is Result.Success<Int> -> {
                    machineInternalState.copy(isLoading = false, count = it.data)
                }
            }
        }
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

                is Result.Success<Int> -> {
                    machineInternalState.copy(isLoading = false, count = it.data)
                }
            }
        }
    private val navigateDetailFlow = actionFlow
        .filterIsInstance<SampleCountActionEvent.NavigateDetail>()
        .onEach {
        }

    override val outerNotifyScenarioActionFlow = merge(navigateDetailFlow)
    init {
        initMachine()
    }
    override fun mergeStateChangeScenarioActionsFlow(): Flow<SampleCountMachineState> = merge(addCountActionFlow, refreshActionFlow)
}