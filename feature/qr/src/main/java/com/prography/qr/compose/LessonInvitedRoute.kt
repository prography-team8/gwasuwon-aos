package com.prography.qr.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.prography.qr.LessonInvitedDialog
import com.prography.qr.LessonInvitedIntent
import com.prography.qr.LessonInvitedUiState
import com.prography.qr.LessonInvitedViewModel
import com.prography.ui.GwasuwonTypography
import com.prography.ui.R
import com.prography.ui.component.CommonButton
import com.prography.ui.component.CommonToolbar
import com.prography.ui.component.GwasuwonConfigurationManager
import com.prography.ui.component.JoinErrorDialog
import com.prography.ui.component.LoadingTransparentScreen
import com.prography.ui.configuration.toColor

/**
 * Created by MyeongKi.
 */
@Composable
fun LessonInvitedRoute(
    viewModel: LessonInvitedViewModel
) {
    val uiState = viewModel.machine.uiState.collectAsState().value
    val intent = viewModel.machine.intentInvoker
    when (uiState) {
        is LessonInvitedUiState.LessonInvited -> LessonInvitedScreen(uiState, intent)
        is LessonInvitedUiState.Complete -> LessonInvitedCompleteScreen(intent)
    }
}

@Composable
private fun LessonInvitedScreen(
    uiState: LessonInvitedUiState.LessonInvited,
    intent: (LessonInvitedIntent) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(
                horizontal = dimensionResource(id = R.dimen.common_large_padding)
            )
            .fillMaxSize()
    ) {
        CommonToolbar(
            modifier = Modifier.align(Alignment.TopStart),
            titleRes = R.string.lesson_invited_toolbar
        )
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = stringResource(id = R.string.lesson_invited_desc),
            style = GwasuwonTypography.Body1NormalRegular.textStyle,
            color = GwasuwonConfigurationManager.colors.labelAlternative.toColor()
        )
        CommonButton(modifier = Modifier.align(Alignment.BottomCenter), textResId = R.string.qr_recognition, isAvailable = true) {
            intent(LessonInvitedIntent.ClickQrRecognition)
        }
    }
    when (uiState.dialog) {
        is LessonInvitedDialog.JoinLessonErrorDialog -> {
            JoinErrorDialog(onClickConfirm = {
                intent(LessonInvitedIntent.ClickDialog)
            }, onClickBackground = {
                intent(LessonInvitedIntent.ClickDialog)
            })
        }

        is LessonInvitedDialog.None -> Unit
    }
    if (uiState.isLoading) {
        LoadingTransparentScreen()
    }
}

@Composable
private fun LessonInvitedCompleteScreen(
    intent: (LessonInvitedIntent) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(
                horizontal = dimensionResource(id = R.dimen.common_large_padding)
            )
            .fillMaxSize()
    ) {

        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_large),
                contentDescription = "logo",
                modifier = Modifier
                    .size(64.dp),
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = stringResource(id = R.string.invite_complete),
                color = GwasuwonConfigurationManager.colors.labelNormal.toColor(),
                style = GwasuwonTypography.Title3Bold.textStyle

            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(id = R.string.invite_complete_desc),
                color = GwasuwonConfigurationManager.colors.labelNeutral.toColor(),
                style = GwasuwonTypography.Label1NormalRegular.textStyle
            )
        }


        CommonButton(modifier = Modifier.align(Alignment.BottomCenter), textResId = R.string.navigate_calendar, isAvailable = true) {
            intent(LessonInvitedIntent.ClickNavigateCalendar)
        }
    }
}