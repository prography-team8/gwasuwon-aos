package com.prography.ui.component

import androidx.activity.compose.BackHandler
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.prography.ui.GwasuwonTypography
import com.prography.ui.R
import com.prography.ui.configuration.toColor

/**
 * Created by MyeongKi.
 */

@Composable
fun CommonDialog(
    @StringRes titleResId: Int,
    @StringRes contentResId: Int,
    @StringRes positiveResId: Int,
    onClickPositive: () -> Unit,
    onClickBackground: () -> Unit
) {
    DimScreen(
        modifier = Modifier
            .pointerInput(onClickBackground) {
                detectTapGestures(
                    onTap = { onClickBackground() }
                )
            }
    ) {
        Surface(
            modifier = Modifier
                .pointerInput(Unit) { detectTapGestures() }
                .padding(horizontal = 28.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(16.dp),
            color = GwasuwonConfigurationManager.colors.backgroundElevatedAlternative.toColor()
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        top = 24.dp,
                        bottom = 16.dp,
                        start = 16.dp,
                        end = 16.dp
                    )
                    .wrapContentHeight()
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = titleResId),
                    style = GwasuwonTypography.Heading1Bold.textStyle,
                    textAlign = TextAlign.Center,
                    color = GwasuwonConfigurationManager.colors.labelStrong.toColor()
                )
                SpaceHeight(height = 8)
                Text(
                    text = stringResource(id = contentResId),
                    style = GwasuwonTypography.Label1NormalRegular.textStyle,
                    textAlign = TextAlign.Center,
                    color = GwasuwonConfigurationManager.colors.labelNeutral.toColor()
                )
                SpaceHeight(height = 32)
                DialogColorButton(
                    modifier = Modifier.fillMaxWidth(),
                    textRes = positiveResId,
                    color = GwasuwonConfigurationManager.colors.primaryNormal.toColor(),
                    onClick = onClickPositive
                )
            }
        }
    }
    BackHandler {
        onClickBackground()
    }
}

@Composable
fun ErrorDialog(
    @StringRes titleResId: Int,
    @StringRes contentResId: Int,
    @StringRes positiveResId: Int,
    onClickPositive: () -> Unit,
    onClickBackground: () -> Unit
) {
    DimScreen(
        modifier = Modifier
            .pointerInput(onClickBackground) {
                detectTapGestures(
                    onTap = { onClickBackground() }
                )
            }
    ) {
        Surface(
            modifier = Modifier
                .pointerInput(Unit) { detectTapGestures() }
                .padding(horizontal = 28.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(16.dp),
            color = GwasuwonConfigurationManager.colors.backgroundElevatedAlternative.toColor()
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        top = 24.dp,
                        bottom = 16.dp,
                        start = 16.dp,
                        end = 16.dp
                    )
                    .wrapContentHeight()
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier
                        .size(32.dp),
                    painter = painterResource(id = R.drawable.icon_error),
                    contentDescription = "error icon"
                )
                SpaceHeight(height = 16)
                Text(
                    text = stringResource(id = titleResId),
                    style = GwasuwonTypography.Heading1Bold.textStyle,
                    textAlign = TextAlign.Center,
                    color = GwasuwonConfigurationManager.colors.labelStrong.toColor()
                )
                SpaceHeight(height = 8)
                Text(
                    text = stringResource(id = contentResId),
                    style = GwasuwonTypography.Label1NormalRegular.textStyle,
                    textAlign = TextAlign.Center,
                    color = GwasuwonConfigurationManager.colors.labelNeutral.toColor()
                )
                SpaceHeight(height = 32)
                Row(modifier = Modifier.fillMaxWidth()) {
                    DialogColorButton(
                        modifier = Modifier.weight(1f),
                        textRes = positiveResId,
                        color = GwasuwonConfigurationManager.colors.statusNegative.toColor(),
                        onClick = onClickPositive
                    )
                }
            }
        }
    }
    BackHandler {
        onClickBackground()
    }
}

@Composable
fun ErrorDialog(
    @StringRes titleResId: Int,
    @StringRes contentResId: Int,
    @StringRes positiveResId: Int,
    @StringRes negativeResId: Int,
    onClickPositive: () -> Unit,
    onClickNegative: () -> Unit,
    onClickBackground: () -> Unit
) {
    DimScreen(
        modifier = Modifier
            .pointerInput(onClickBackground) {
                detectTapGestures(
                    onTap = { onClickBackground() }
                )
            }
    ) {
        Surface(
            modifier = Modifier
                .pointerInput(Unit) { detectTapGestures() }
                .padding(horizontal = 28.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(16.dp),
            color = GwasuwonConfigurationManager.colors.backgroundElevatedAlternative.toColor()
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        top = 24.dp,
                        bottom = 16.dp,
                        start = 16.dp,
                        end = 16.dp
                    )
                    .wrapContentHeight()
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier
                        .size(32.dp),
                    painter = painterResource(id = R.drawable.icon_error),
                    contentDescription = "error icon"
                )
                SpaceHeight(height = 16)
                Text(
                    text = stringResource(id = titleResId),
                    style = GwasuwonTypography.Heading1Bold.textStyle,
                    textAlign = TextAlign.Center,
                    color = GwasuwonConfigurationManager.colors.labelStrong.toColor()
                )
                SpaceHeight(height = 8)
                Text(
                    text = stringResource(id = contentResId),
                    style = GwasuwonTypography.Label1NormalRegular.textStyle,
                    textAlign = TextAlign.Center,
                    color = GwasuwonConfigurationManager.colors.labelNeutral.toColor()
                )
                SpaceHeight(height = 32)
                Row(modifier = Modifier.fillMaxWidth()) {
                    DialogBordButton(
                        modifier = Modifier.weight(1f),
                        textRes = negativeResId,
                        onClick = onClickNegative
                    )
                    SpaceWidth(width = 12)
                    DialogColorButton(
                        modifier = Modifier.weight(1f),
                        textRes = positiveResId,
                        color = GwasuwonConfigurationManager.colors.statusNegative.toColor(),
                        onClick = onClickPositive
                    )
                }
            }
        }
    }
    BackHandler {
        onClickBackground()
    }
}

@Composable
private fun DialogBordButton(
    modifier: Modifier,
    textRes: Int,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .wrapContentHeight()
            .border(
                width = 1.dp,
                color = GwasuwonConfigurationManager.colors.lineRegularNormal.toColor(),
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.common_btn_conner))
            )
            .background(
                GwasuwonConfigurationManager.colors.backgroundRegularNormal.toColor()
            )
            .padding(dimensionResource(id = R.dimen.common_padding))
            .clickable(onClick = onClick)
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = stringResource(id = textRes),
            color = GwasuwonConfigurationManager.colors.labelNormal.toColor(),
            style = GwasuwonTypography.Label1NormalBold.textStyle
        )
    }
}

@Composable
private fun DialogColorButton(
    modifier: Modifier,
    textRes: Int,
    color: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .wrapContentHeight()
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.common_btn_conner)))
            .background(color)
            .padding(dimensionResource(id = R.dimen.common_padding))
            .clickable(onClick = onClick)
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = stringResource(id = textRes),
            color = GwasuwonConfigurationManager.colors.staticWhite.toColor(),
            style = GwasuwonTypography.Label1NormalBold.textStyle
        )
    }
}

@Composable
fun NetworkErrorDialog(
    onClickConfirm: () -> Unit,
    onClickBackground: () -> Unit
) {
    ErrorDialog(
        titleResId = R.string.network_error_title,
        contentResId = R.string.network_error_desc,
        positiveResId = R.string.common_confirm,
        onClickPositive = onClickConfirm,
        onClickBackground = onClickBackground
    )
}

@Composable
fun UnknownErrorDialog(
    onClickConfirm: () -> Unit,
    onClickBackground: () -> Unit
) {
    ErrorDialog(
        titleResId = R.string.unknown_error_title,
        contentResId = R.string.unknown_error_desc,
        positiveResId = R.string.common_confirm,
        onClickPositive = onClickConfirm,
        onClickBackground = onClickBackground
    )
}
@Composable
fun NotifyInvaildLessonErrorDialog(
    onClickConfirm: () -> Unit,
    onClickBackground: () -> Unit
) {
    ErrorDialog(
        titleResId = R.string.invalid_lesson_title,
        contentResId = R.string.invalid_lesson_desc,
        positiveResId = R.string.common_confirm,
        onClickPositive = onClickConfirm,
        onClickBackground = onClickBackground
    )
}

@Composable
fun JoinErrorDialog(
    onClickConfirm: () -> Unit,
    onClickBackground: () -> Unit
) {
    ErrorDialog(
        titleResId = R.string.join_lesson_error_title,
        contentResId = R.string.join_lesson_error_desc,
        positiveResId = R.string.common_confirm,
        onClickPositive = onClickConfirm,
        onClickBackground = onClickBackground
    )
}
@Composable
fun CertificateLessonErrorDialog(
    onClickConfirm: () -> Unit,
    onClickBackground: () -> Unit
) {
    ErrorDialog(
        titleResId = R.string.certificate_lesson_error_title,
        contentResId = R.string.certificate_lesson_error_desc,
        positiveResId = R.string.common_confirm,
        onClickPositive = onClickConfirm,
        onClickBackground = onClickBackground
    )
}
sealed interface CommonDialogState {
    data object NetworkError : CommonDialogState
    data object UnknownError : CommonDialogState
}

sealed interface CommonDialogIntent {
    data object ClickConfirm : CommonDialogIntent
    data object ClickBackground : CommonDialogIntent
}

@Composable
fun CommonDialogRoute(
    dialog: CommonDialogState,
    intent:(CommonDialogIntent)->Unit
) {
    when (dialog) {
        is CommonDialogState.NetworkError -> {
            NetworkErrorDialog(
                onClickConfirm = {
                    intent(CommonDialogIntent.ClickConfirm)
                },
                onClickBackground = {
                    intent(CommonDialogIntent.ClickBackground)
                }
            )
        }
        is CommonDialogState.UnknownError -> {
            UnknownErrorDialog(
                onClickConfirm = {
                    intent(CommonDialogIntent.ClickConfirm)
                },
                onClickBackground = {
                    intent(CommonDialogIntent.ClickBackground)
                }
            )
        }
    }
}