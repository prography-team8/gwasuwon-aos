package com.prography.qr

import android.graphics.Bitmap
import androidx.compose.runtime.Immutable
import com.prography.usm.state.MachineInternalState
import com.prography.usm.state.UiState

/**
 * Created by MyeongKi.
 */
data class OnlyQrMachineState(
    val qr: Bitmap? = null
) : MachineInternalState<OnlyQrUiState> {
    override fun toUiState(): OnlyQrUiState = OnlyQrUiState(qr)
}
@Immutable
data class OnlyQrUiState(
    val qr: Bitmap? = null
) : UiState