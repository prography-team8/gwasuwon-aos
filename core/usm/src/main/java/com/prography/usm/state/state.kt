package com.prography.usm.state

/**
 * Created by MyeongKi.
 */
interface UiState
interface MachineInternalState<T : UiState> {
    fun toUiState(): T
}