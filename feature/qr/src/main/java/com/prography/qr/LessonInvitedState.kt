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
    val dialog: LessonInvitedDialog = LessonInvitedDialog.None
) : MachineInternalState<LessonInvitedUiState> {
    override fun toUiState(): LessonInvitedUiState {
        return if (isParticipateLesson) {
            LessonInvitedUiState.Complete
        } else {
            LessonInvitedUiState.LessonInvited(isLoading, dialog)
        }
    }
}

sealed interface LessonInvitedUiState : UiState {
    data class LessonInvited(
        val isLoading: Boolean, val dialog: LessonInvitedDialog
    ) : LessonInvitedUiState

    data object Complete : LessonInvitedUiState
}

sealed interface LessonInvitedDialog {
    data object None : LessonInvitedDialog
    data object JoinLessonErrorDialog : LessonInvitedDialog
}