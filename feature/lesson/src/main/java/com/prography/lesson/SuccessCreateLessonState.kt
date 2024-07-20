package com.prography.lesson

import com.prography.usm.state.MachineInternalState
import com.prography.usm.state.UiState

/**
 * Created by MyeongKi.
 */
data class SuccessCreateLessonMachineState(
    val contractUrl: String = "",
    val isLoading: Boolean = false
) : MachineInternalState<SuccessCreateLessonUiState> {
    override fun toUiState(): SuccessCreateLessonUiState = SuccessCreateLessonUiState(
        isLoading = isLoading
    )
}

data class SuccessCreateLessonUiState(
    val isLoading: Boolean = false
) : UiState