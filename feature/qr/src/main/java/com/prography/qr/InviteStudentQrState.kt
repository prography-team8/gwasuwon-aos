package com.prography.qr

import android.graphics.Bitmap
import androidx.compose.runtime.Immutable
import com.prography.usm.state.MachineInternalState
import com.prography.usm.state.UiState

/**
 * Created by MyeongKi.
 */
data class InviteStudentQrMachineState(
    val qr: Bitmap? = null
) : MachineInternalState<InviteStudentQrUiState> {
    override fun toUiState(): InviteStudentQrUiState = InviteStudentQrUiState(qr)
}
@Immutable
data class InviteStudentQrUiState(
    val qr: Bitmap? = null
) : UiState