package com.prography.account

import com.prography.usm.state.MachineInternalState
import com.prography.usm.state.UiState

/**
 * Created by MyeongKi.
 */

data class SignInMachineState(
    val isLoading: Boolean,
) : MachineInternalState<SignInUiState> {
    override fun toUiState(): SignInUiState {
        return SignInUiState(isLoading = isLoading)
    }
}

data class SignInUiState(
    val isLoading: Boolean,
) : UiState