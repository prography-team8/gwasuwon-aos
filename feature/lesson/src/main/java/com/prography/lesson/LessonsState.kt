package com.prography.lesson

import com.prography.usm.state.MachineInternalState
import com.prography.usm.state.UiState

/**
 * Created by MyeongKi.
 */
data object LessonsMachineState : MachineInternalState<LessonsUiState> {
    override fun toUiState(): LessonsUiState = LessonsUiState
}

object LessonsUiState : UiState