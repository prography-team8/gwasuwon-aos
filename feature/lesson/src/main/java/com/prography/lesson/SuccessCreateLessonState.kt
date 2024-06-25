package com.prography.lesson

import com.prography.usm.state.MachineInternalState
import com.prography.usm.state.UiState

/**
 * Created by MyeongKi.
 */
object SuccessCreateLessonMachineState : MachineInternalState<SuccessCreateLessonUiState> {
    override fun toUiState(): SuccessCreateLessonUiState = SuccessCreateLessonUiState
}

object SuccessCreateLessonUiState : UiState