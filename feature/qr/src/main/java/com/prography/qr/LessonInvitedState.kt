package com.prography.qr

import com.prography.usm.state.MachineInternalState
import com.prography.usm.state.UiState

/**
 * Created by MyeongKi.
 */
data class LessonInvitedMachineState(
    val isParticipateLesson: Boolean = false,
    val lessonId: Long? = null,
    val isLoading: Boolean = false,
) : MachineInternalState<LessonInvitedUiState> {
    override fun toUiState(): LessonInvitedUiState {
        return if (isParticipateLesson) {
            LessonInvitedUiState.Complete
        } else {
            LessonInvitedUiState.LessonInvited(isLoading)
        }
    }
}

sealed interface LessonInvitedUiState : UiState {
    data class LessonInvited(val isLoading: Boolean) : LessonInvitedUiState
    data object Complete : LessonInvitedUiState
}