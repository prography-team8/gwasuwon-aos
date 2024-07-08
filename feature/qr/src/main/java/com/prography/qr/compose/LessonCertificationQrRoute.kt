package com.prography.qr.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.prography.qr.LessonCertificationQrActionEvent
import com.prography.qr.LessonCertificationQrIntent
import com.prography.qr.LessonCertificationQrViewModel
import com.prography.qr.OnlyQrUiState
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
fun LessonCertificationQrRoute(
    viewModel: LessonCertificationQrViewModel
) {
    val uiState = viewModel.machine.uiState.collectAsState()
    LaunchedEffect(true) {
        viewModel.machine.eventInvoker(LessonCertificationQrActionEvent.GenerateQr)
    }
    LessonCertificationQrScreen(
        uiState = uiState.value,
        intent = viewModel.machine.intentInvoker,
    )
}

@Composable
private fun LessonCertificationQrScreen(
    uiState: OnlyQrUiState,
    intent: (LessonCertificationQrIntent) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(
                horizontal = dimensionResource(id = R.dimen.common_large_padding)
            )
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LessonCertificationQrHeader {
            intent(LessonCertificationQrIntent.ClickBack)
        }
        SpaceHeight(height = 136)
        InviteStudentDesc()
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
private fun LessonCertificationQrHeader(
    onClickBack: () -> Unit
) {
    CommonToolbar(titleRes = R.string.lesson_certification_qr, onClickBack = onClickBack)
}

@Composable
private fun InviteStudentDesc() {
    Text(
        textAlign = TextAlign.Center,
        text = stringResource(id = R.string.lesson_certification_qr_desc),
        style = GwasuwonTypography.Caption1Regular.textStyle,
        color = GwasuwonConfigurationManager.colors.labelNeutral.toColor()
    )
}