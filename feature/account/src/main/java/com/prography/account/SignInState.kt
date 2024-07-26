package com.prography.account

import com.prography.ui.component.CommonDialogState
import com.prography.usm.state.MachineInternalState
import com.prography.usm.state.UiState

/**
 * Created by MyeongKi.
 */

data class SignInMachineState(
    val isLoading: Boolean,
    val dialog: SignInDialog
) : MachineInternalState<SignInUiState> {
    override fun toUiState(): SignInUiState {
        return SignInUiState(
            isLoading = isLoading,
            dialog = dialog
        )
    }
}

data class SignInUiState(
    val isLoading: Boolean,
    val dialog: SignInDialog
) : UiState

sealed interface SignInDialog {
    data object None : SignInDialog
    data class SignInCommonDialog(
        val state: CommonDialogState
    ) : SignInDialog
}