package com.prography.usm_sample.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.prography.configuration.toColor
import com.prography.configuration.ui.GwasuwonConfigurationManager
import com.prography.usm_sample.SampleCountIntent
import com.prography.usm_sample.SampleCountUiState
import com.prography.usm_sample.SampleCountViewModel

/**
 * Created by MyeongKi.
 */
@Composable
fun SampleCountRoute(
    viewModel: SampleCountViewModel
) {
    val uiState = viewModel.machine.uiState.collectAsState().value
    val intentInvoker = viewModel.machine.intentInvoker
    when (uiState) {
        is SampleCountUiState.Loading -> {
            Text(text = "Loading")
        }

        is SampleCountUiState.SampleCountScreen -> {
            SampleCountScreen(
                uiState = uiState,
                intentInvoker = intentInvoker
            )
        }
    }
}

@Composable
private fun SampleCountScreen(
    uiState: SampleCountUiState.SampleCountScreen,
    intentInvoker: (SampleCountIntent) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier.clickable {
                intentInvoker(SampleCountIntent.ClickAddBtn)
            },
            text = uiState.count,
            color = GwasuwonConfigurationManager.colors.textColor.toColor()
        )
    }
}