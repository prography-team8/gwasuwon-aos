package com.prography.usm_sample

import com.prography.usm.state.MachineInternalState
import com.prography.usm.state.UiState

/**
 * Created by MyeongKi.
 */
sealed interface SampleCountUiState : UiState {
    data object Loading : SampleCountUiState
    data class SampleCountScreen(
        val count: String
    ) : SampleCountUiState
}

data class SampleCountMachineState(
    val isLoading: Boolean,
    val count: Int
) : MachineInternalState<SampleCountUiState> {
    override fun toUiState(): SampleCountUiState {
        return if (isLoading) {
            SampleCountUiState.Loading
        } else {
            SampleCountUiState.SampleCountScreen(count.toString())
        }
    }
}