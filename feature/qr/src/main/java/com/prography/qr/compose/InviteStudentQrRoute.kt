package com.prography.qr.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import com.prography.qr.InviteStudentQrActionEvent
import com.prography.qr.InviteStudentQrIntent
import com.prography.qr.InviteStudentQrUiState
import com.prography.qr.InviteStudentQrViewModel
import com.prography.ui.GwasuwonTypography
import com.prography.ui.R
import com.prography.ui.component.CommonButton
import com.prography.ui.component.CommonToolbar
import com.prography.ui.component.GwasuwonConfigurationManager
import com.prography.ui.component.SpaceHeight
import com.prography.ui.configuration.toColor

/**
 * Created by MyeongKi.
 */
@Composable
fun InviteStudentQrRoute(
    viewModel: InviteStudentQrViewModel
) {
    val uiState = viewModel.machine.uiState.collectAsState()
    LaunchedEffect(true) {
        viewModel.machine.eventInvoker(InviteStudentQrActionEvent.GenerateQr)
    }
    InviteStudentQrScreen(
        uiState = uiState.value,
        intent = viewModel.machine.intentInvoker,
    )
}

@Composable
private fun InviteStudentQrScreen(
    uiState: InviteStudentQrUiState,
    intent: (InviteStudentQrIntent) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(
                horizontal = dimensionResource(id = R.dimen.common_large_padding)
            )
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        InviteStudentQrHeader {
            intent(InviteStudentQrIntent.ClickBack)
        }
        SpaceHeight(height = 50)
        InviteStudentDesc()
        SpaceHeight(height = 32)
        uiState.qr?.let {
            Image(
                modifier = Modifier.size(240.dp),
                bitmap = it.asImageBitmap(),
                contentDescription = "QR Code"
            )
        }

        Spacer(modifier = Modifier.weight(1f))
        CommonButton(textResId = R.string.navigate_home, isAvailable = true) {
            intent(InviteStudentQrIntent.ClickHome)
        }
    }
}

@Composable
private fun InviteStudentQrHeader(
    onClickBack: () -> Unit
) {
    CommonToolbar(titleRes = R.string.invite_student_qr, onClickBack = onClickBack)
}

@Composable
private fun InviteStudentDesc() {
    Text(
        textAlign = TextAlign.Center,
        text = stringResource(id = R.string.invite_student_qr_desc),
        style = GwasuwonTypography.Caption1Regular.textStyle,
        color = GwasuwonConfigurationManager.colors.labelNeutral.toColor()
    )
}