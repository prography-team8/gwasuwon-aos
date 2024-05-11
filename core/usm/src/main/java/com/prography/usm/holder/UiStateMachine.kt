package com.prography.usm.holder

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


/**
 * Created by MyeongKi.
 */
abstract class UiStateMachine<
        UiState : com.prography.usm.state.UiState,
        MachineInternalState : com.prography.usm.state.MachineInternalState<UiState>,
        ActionEvent : com.prography.usm.action.ActionEvent,
        Intent : com.prography.usm.action.Intent<ActionEvent>
        >(
    private val scope: CoroutineScope,
    fromOutsideActionFlowMerged: Flow<ActionEvent>? = null
) {
    protected abstract var machineInternalState: MachineInternalState
    private val intentFlow: MutableSharedFlow<Intent> = MutableSharedFlow()
    val intentInvoker: (Intent) -> Unit = {
        scope.launch {
            intentFlow.emit(it)
        }
    }
    private val eventFlow: MutableSharedFlow<ActionEvent> = MutableSharedFlow()
    val eventInvoker: (ActionEvent) -> Unit = {
        scope.launch {
            eventFlow.emit(it)
        }
    }
    protected val actionFlow = merge(
        intentFlow.map { it.toActionEvent() },
        eventFlow,
    ).also {
        fromOutsideActionFlowMerged?.let {
            merge(it)
        }
    }

    val uiState: StateFlow<UiState> by lazy {
        mergeScenarioActionsFlow()
            .onEach { machineInternalState = it }
            .map { it.toUiState() }
            .stateIn(
                scope,
                SharingStarted.Eagerly,
                machineInternalState.toUiState()
            )
    }

    protected abstract fun mergeScenarioActionsFlow(): Flow<MachineInternalState>
}