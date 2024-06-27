package com.prography.qr.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.prography.qr.LessonContractQrActionEvent
import com.prography.qr.LessonContractQrIntent
import com.prography.qr.LessonContractQrUiState
import com.prography.qr.LessonContractQrViewModel
import com.prography.ui.GwasuwonTypography
import com.prography.ui.R
import com.prography.ui.component.CommonToolbar
import com.prography.ui.component.GwasuwonConfigurationManager
import com.prography.ui.component.SpaceHeight
import com.prography.ui.configuration.toColor

/**
 * Created by MyeongKi.
 */
@Composable
fun LessonContractQrRoute(
    viewModel: LessonContractQrViewModel
) {
    val uiState = viewModel.machine.uiState.collectAsState()
    LaunchedEffect(true) {
        viewModel.machine.eventInvoker(LessonContractQrActionEvent.GenerateQr)
    }
    LessonContractQrScreen(
        uiState = uiState.value,
        intent = viewModel.machine.intentInvoker,
    )
}

@Composable
fun LessonContractQrScreen(
    uiState: LessonContractQrUiState,
    intent: (LessonContractQrIntent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LessonContractQrHeader {
            intent(LessonContractQrIntent.ClickBack)
        }
        SpaceHeight(height = 50)
        LessonContractDesc()
        SpaceHeight(height = 32)
        uiState.qr?.let {
            Image(
                modifier = Modifier.size(240.dp),
                bitmap = it.asImageBitmap(),
                contentDescription = "QR Code"
            )
        }
    }
}

@Composable
private fun LessonContractQrHeader(
    onClickBack: () -> Unit
) {
    CommonToolbar(titleRes = R.string.lesson_contract, onClickBack = onClickBack)
}

@Composable
private fun LessonContractDesc() {
    Text(
        textAlign = TextAlign.Center,
        text = stringResource(id = R.string.lesson_contract_qr_desc),
        style = GwasuwonTypography.Caption1Regular.textStyle,
        color = GwasuwonConfigurationManager.colors.labelNeutral.toColor()
    )
}