package com.prography.lesson.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.prography.ui.R
import com.prography.ui.configuration.toColor
import com.prography.ui.component.GwasuwonConfigurationManager
import com.prography.lesson.LessonItemIntent
import com.prography.lesson.LessonItemUiMachine
import com.prography.lesson.LessonItemUiState
import com.prography.lesson.utils.getLessonDayStringRes
import com.prography.lesson.utils.getLessonDurationStringRes
import com.prography.lesson.utils.getLessonSubjectStringRes
import com.prography.ui.GwasuwonTypography

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
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(GwasuwonConfigurationManager.colors.backgroundElevatedAlternative.toColor())
            .padding(
                vertical = dimensionResource(id = R.dimen.common_padding),
                horizontal = dimensionResource(id = R.dimen.common_large_padding)
            )
            .clickable(onClick = {
                intent(LessonItemIntent.ClickLesson)
            })
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    modifier = Modifier
                        .wrapContentSize(),
                    text = uiState.lesson.studentName,
                    color = GwasuwonConfigurationManager.colors.labelNormal.toColor(),
                    style = GwasuwonTypography.Headline1Bold.textStyle
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    modifier = Modifier
                        .wrapContentSize(),
                    text = "${uiState.lesson.schoolYear}/${stringResource(id = uiState.lesson.lessonSubject.getLessonSubjectStringRes())}",
                    color = GwasuwonConfigurationManager.colors.labelNormal.toColor(),
                    style = GwasuwonTypography.Caption1Regular.textStyle
                )
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
                        info = uiState.lesson.lessonDay.getLessonDayStringRes().map { stringResource(id = it) }.joinToString(separator = ",")
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    LessonPointInfoItem(
                        pointColor = colorResource(id = R.color.orange_50),
                        info = stringResource(id = uiState.lesson.lessonDuration.getLessonDurationStringRes())
                    )
                }
                Text(
                    modifier = Modifier
                        .wrapContentSize(),
                    text = "${uiState.lesson.restLesson}/${uiState.lesson.lessonNumberOfProgress}",
                    color = GwasuwonConfigurationManager.colors.labelNormal.toColor(),
                    style = GwasuwonTypography.Label2Regular.textStyle
                )
            }
        }
    }
}

@Composable
private fun LessonPointInfoItem(
    pointColor: Color = Color.Unspecified,
    info: String
) {
    Box(
        modifier = Modifier
            .wrapContentSize()
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.common_btn_conner)))
            .background(pointColor)
            .padding(
                vertical = dimensionResource(id = R.dimen.common_small_padding),
                horizontal = dimensionResource(id = R.dimen.common_padding)
            )
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = info,
            color = GwasuwonConfigurationManager.colors.staticWhite.toColor(),
            style = GwasuwonTypography.Label2Regular.textStyle
        )
    }
}