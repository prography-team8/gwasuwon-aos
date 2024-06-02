package com.prography.usm.state

/**
 * Created by MyeongKi.
 */
interface UiState
interface MachineInternalState<T : UiState> {
    fun toUiState(): T
}

data object StubMachineInternalState : MachineInternalState<StubUiState> {
    override fun toUiState(): StubUiState {
        return StubUiState
    }
}

data object StubUiState : UiState
