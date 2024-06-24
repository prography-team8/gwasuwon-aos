package com.prography.lesson

import com.prography.usm.state.MachineInternalState
import com.prography.usm.state.UiState

/**
 * Created by MyeongKi.
 */
data class LessonsMachineState(
    val isRequestRefresh: Boolean = false
) : MachineInternalState<LessonsUiState> {
    override fun toUiState(): LessonsUiState {
        return LessonsUiState(isRequestRefresh)
    }
}

data class LessonsUiState(
    val isRequestRefresh: Boolean = false
) : UiState