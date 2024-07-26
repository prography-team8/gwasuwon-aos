package com.prography.qr

import android.graphics.Bitmap
import androidx.compose.runtime.Immutable
import com.prography.usm.state.MachineInternalState
import com.prography.usm.state.UiState

/**
 * Created by MyeongKi.
 */
data class LessonContractQrMachineState(
    val qr: Bitmap? = null,
    val url: String? = null,
    val isLoading: Boolean = false,
) : MachineInternalState<LessonContractQrUiState> {
    override fun toUiState(): LessonContractQrUiState = LessonContractQrUiState(qr, isLoading)
}

@Immutable
data class LessonContractQrUiState(
    val qr: Bitmap?,
    val isLoading: Boolean,
) : UiState