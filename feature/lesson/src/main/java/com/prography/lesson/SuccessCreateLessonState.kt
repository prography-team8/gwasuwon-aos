package com.prography.lesson

import com.prography.usm.state.MachineInternalState
import com.prography.usm.state.UiState

/**
 * Created by MyeongKi.
 */
data class SuccessCreateLessonMachineState(
    val contractUrl: String = "",
    val isLoading: Boolean = false,
    val dialog: SuccessCreateLessonDialog = SuccessCreateLessonDialog.None
) : MachineInternalState<SuccessCreateLessonUiState> {
    override fun toUiState(): SuccessCreateLessonUiState = SuccessCreateLessonUiState(
        isLoading = isLoading,
        dialog = dialog
    )
}

data class SuccessCreateLessonUiState(
    val isLoading: Boolean = false,
    val dialog: SuccessCreateLessonDialog = SuccessCreateLessonDialog.None
) : UiState

sealed interface SuccessCreateLessonDialog {
    data object None : SuccessCreateLessonDialog
    data object SuccessCopy : SuccessCreateLessonDialog
}