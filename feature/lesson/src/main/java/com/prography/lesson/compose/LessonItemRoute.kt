package com.prography.lesson.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.prography.lesson.LessonItemIntent
import com.prography.lesson.LessonItemUiMachine
import com.prography.lesson.LessonItemUiState
import com.prography.lesson.utils.getLessonDayStringRes
import com.prography.lesson.utils.getLessonDurationStringRes
import com.prography.lesson.utils.getLessonSubjectStringRes
import com.prography.ui.GwasuwonTypography
import com.prography.ui.R
import com.prography.ui.component.GwasuwonConfigurationManager
import com.prography.ui.configuration.toColor

/**
 * Created by MyeongKi.
 */
@Composable
fun LessonItemRoute(
    machine: LessonItemUiMachine
) {
    val uiState = machine.uiState.collectAsState().value
    LessonItemScreen(
        uiState = uiState,
        intent = machine.intentInvoker
    )
}

@Composable
private fun LessonItemScreen(
    uiState: LessonItemUiState,
    intent: (LessonItemIntent) -> Unit
) {
    val available = uiState.lesson.numberOfSessionsCompleted < uiState.lesson.numberOfSessions
    val labelTextColor = if (available)
        GwasuwonConfigurationManager.colors.labelNormal.toColor()
    else
        GwasuwonConfigurationManager.colors.labelDisable.toColor().copy(alpha = 0.16f)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.common_btn_conner)))
            .background(
                if (available) {
                    GwasuwonConfigurationManager.colors.backgroundElevatedAlternative.toColor()
                } else {
                    GwasuwonConfigurationManager.colors.interactionInactive.toColor()
                }
            )
            .padding(
                vertical = dimensionResource(id = R.dimen.common_padding),
                horizontal = dimensionResource(id = R.dimen.common_large_padding)
            )
            .clickable(onClick = {
                intent(LessonItemIntent.ClickLesson)
            })
    ) {
        Column {
            Row {
                Row(
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        modifier = Modifier
                            .wrapContentSize(),
                        text = uiState.lesson.studentName,
                        color = labelTextColor,
                        style = GwasuwonTypography.Headline1Bold.textStyle
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        modifier = Modifier
                            .wrapContentSize(),
                        text = "${uiState.lesson.grade}/${stringResource(id = uiState.lesson.subject.getLessonSubjectStringRes())}",
                        color = labelTextColor,
                        style = GwasuwonTypography.Caption1Regular.textStyle
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                if (available.not()) {
                    LessonExtensionRequiredButton {

                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    LessonPointInfoItem(
                        pointColor = colorResource(id = R.color.green_50),
                        info = uiState.lesson.classDays.getLessonDayStringRes().map { stringResource(id = it) }.joinToString(separator = ","),
                        available = available
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    LessonPointInfoItem(
                        pointColor = colorResource(id = R.color.orange_50),
                        info = stringResource(id = uiState.lesson.sessionDuration.getLessonDurationStringRes()),
                        available = available
                    )
                }
                Text(
                    modifier = Modifier
                        .wrapContentSize(),
                    text = "${uiState.lesson.numberOfSessionsCompleted}/${uiState.lesson.numberOfSessions}",
                    color = labelTextColor,
                    style = GwasuwonTypography.Label2Regular.textStyle
                )
            }
        }
    }
}

@Composable
private fun LessonPointInfoItem(
    pointColor: Color = Color.Unspecified,
    info: String,
    available: Boolean
) {
    Box(
        modifier = Modifier
            .wrapContentSize()
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.common_btn_conner)))
            .background(
                if (available)
                    pointColor
                else
                    GwasuwonConfigurationManager.colors.fillStrong
                        .toColor()
                        .copy(alpha = 0.16f)
            )
            .padding(
                vertical = dimensionResource(id = R.dimen.common_small_padding),
                horizontal = dimensionResource(id = R.dimen.common_padding)
            )
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = info,
            color = if (available)
                GwasuwonConfigurationManager.colors.staticWhite.toColor()
            else
                GwasuwonConfigurationManager.colors.labelDisable.toColor().copy(alpha = 0.16f),
            style = GwasuwonTypography.Label2Regular.textStyle
        )
    }
}

@Composable
private fun LessonExtensionRequiredButton(
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clickable(onClick = onClick)
            .wrapContentSize()
            .clip(RoundedCornerShape(16.dp))
            .background(
                GwasuwonConfigurationManager.colors.statusNegative.toColor()
            )
            .padding(
                vertical = dimensionResource(id = R.dimen.common_small_padding),
                horizontal = dimensionResource(id = R.dimen.common_padding)
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.extension_required),
                color = GwasuwonConfigurationManager.colors.staticWhite.toColor(),
                style = GwasuwonTypography.Caption2Bold.textStyle
            )
            Image(
                painter = painterResource(id = R.drawable.icon_right),
                contentDescription = "LessonExtensionRequiredButton",
                modifier = Modifier
                    .size(14.dp),
                colorFilter = ColorFilter.tint(GwasuwonConfigurationManager.colors.staticWhite.toColor())
            )
        }
    }
}