package com.prography.lesson

import com.prography.domain.lesson.model.Lesson
import com.prography.domain.lesson.model.LessonCard
import com.prography.usm.state.MachineInternalState
import com.prography.usm.state.UiState

/**
 * Created by MyeongKi.
 */
data class LessonItemMachineState(
    val lesson: LessonCard
) : MachineInternalState<LessonItemUiState> {
    override fun toUiState(): LessonItemUiState = LessonItemUiState(lesson)
}

data class LessonItemUiState(
    val lesson: LessonCard
) : UiState