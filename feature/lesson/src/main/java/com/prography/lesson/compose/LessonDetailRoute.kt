package com.prography.lesson.compose

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.prography.lesson.LessonDateInfoUiState
import com.prography.lesson.LessonDetailActionEvent
import com.prography.lesson.LessonDetailDialog
import com.prography.lesson.LessonDetailIntent
import com.prography.lesson.LessonDetailUiState
import com.prography.lesson.LessonDetailViewModel
import com.prography.ui.GwasuwonTypography
import com.prography.ui.R
import com.prography.ui.component.CommonButton
import com.prography.ui.component.CommonToolbar
import com.prography.ui.component.DropdownMoreComponent
import com.prography.ui.component.ErrorDialog
import com.prography.ui.component.GwasuwonConfigurationManager
import com.prography.ui.component.SpaceHeight
import com.prography.ui.configuration.toColor
import com.prography.utils.date.toDisplayKrMonthDate
import kotlinx.collections.immutable.persistentListOf

/**
 * Created by MyeongKi.
 */
@Composable
fun LessonDetailRoute(
    viewModel: LessonDetailViewModel
) {
    val uiState = viewModel.machine.uiState.collectAsState()
    LaunchedEffect(true) {
        viewModel.machine.eventInvoker(LessonDetailActionEvent.Refresh)
    }
    LessonDetailScreen(
        uiState = uiState.value,
        event = viewModel.machine.eventInvoker,
        intent = viewModel.machine.intentInvoker
    )
    LessonDetailDialogRoute(
        uiState.value.dialog,
        viewModel.machine.intentInvoker,
        viewModel.machine.eventInvoker
    )
}

@Composable
private fun LessonDetailDialogRoute(
    dialogState: LessonDetailDialog,
    intent: (LessonDetailIntent) -> Unit,
    event: (LessonDetailActionEvent) -> Unit
) {
    when (dialogState) {
        is LessonDetailDialog.DeleteLesson -> {
            ErrorDialog(
                titleResId = R.string.delete_lesson,
                contentResId = R.string.delete_lesson_dialog_desc,
                positiveResId = R.string.delete,
                negativeResId = R.string.cancel,
                onClickPositive = {
                    event(LessonDetailActionEvent.HideDialog)
                    intent(LessonDetailIntent.ClickDeleteDialogConfirm)
                },
                onClickNegative = {
                    intent(LessonDetailIntent.ClickDialogCancel)
                },
                onClickBackground = {
                    intent(LessonDetailIntent.ClickDialogBackground)
                }
            )
        }

        else -> Unit
    }
}

@Composable
private fun LessonDetailScreen(
    uiState: LessonDetailUiState,
    event: (LessonDetailActionEvent) -> Unit,
    intent: (LessonDetailIntent) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(
                horizontal = dimensionResource(id = R.dimen.common_large_padding)
            )
            .fillMaxSize()
    ) {
        CommonToolbar(
            title = stringResource(id = R.string.student_title, uiState.studentName),
            onClickBack = {
                intent(LessonDetailIntent.ClickBack)
            },
            isVisibleRight = true,
        ) {
            DropdownMoreComponent(
                optionRes = persistentListOf(
                    R.string.lesson_info_detail_title,
                    R.string.delete_lesson
                )
            ) {
                when (it) {
                    0 -> {
                        intent(LessonDetailIntent.ClickLessonInfoDetail)
                    }

                    1 -> {
                        intent(LessonDetailIntent.ClickDeleteLesson)
                    }

                    else -> Unit
                }
            }
        }
        LessonDetailCalendar(
            focusDate = uiState.focusDate,
            lessonDates = uiState.lessonDates,
            lessonAttendanceDates = uiState.lessonAttendanceDates,
            lessonAbsentDates = uiState.lessonAbsentDates,
        ) {
            intent(LessonDetailIntent.ClickDate(it))
        }
        SpaceHeight(height = 16)
        LessonDetailCalendarDesc()
        SpaceHeight(height = 24)
        LessonDateInfoRoute(
            modifier = Modifier.weight(1f),
            itemState = uiState.lessonDateInfoUiState,
            focusDate = uiState.focusDate,
            intent = intent
        )
    }
}


@Composable
private fun LessonDateInfoRoute(
    modifier: Modifier,
    itemState: LessonDateInfoUiState,
    focusDate: Long,
    intent: (LessonDetailIntent) -> Unit
) {
    when (itemState) {
        is LessonDateInfoUiState.NoLesson -> {
            NoLessonItem(
                modifier = modifier,
                focusDate = focusDate
            )
        }

        is LessonDateInfoUiState.ScheduleLesson -> {
            ScheduleLessonItem(
                modifier = modifier,
                focusDate = focusDate
            )
        }

        is LessonDateInfoUiState.AbsentLesson -> {
            AbsentLessonItem(
                modifier = modifier,
                focusDate = focusDate
            ) {
                intent(LessonDetailIntent.ClickCheckByAttendance)
            }
        }

        is LessonDateInfoUiState.CompletedLesson -> {
            CompletedLessonItem(
                modifier = modifier,
                itemState = itemState,
                focusDate = focusDate
            )
        }
    }
}

@Composable
private fun NoLessonItem(
    modifier: Modifier,
    focusDate: Long,
) {
    val dateString = focusDate.toDisplayKrMonthDate()
    Column(modifier = modifier) {
        LessonItem(
            title = dateString,
            descRes = R.string.no_lesson_desc
        )
    }
}

@Composable
private fun ScheduleLessonItem(
    modifier: Modifier,
    focusDate: Long
) {
    val dateString = focusDate.toDisplayKrMonthDate()
    Column(modifier = modifier) {
        LessonItem(
            title = dateString,
            descRes = R.string.schedule_lesson_desc
        )
    }
}

@Composable
private fun AbsentLessonItem(
    modifier: Modifier,
    focusDate: Long,
    onClickCheckByAttendance: () -> Unit
) {
    val dateString = focusDate.toDisplayKrMonthDate()
    Column(modifier = modifier) {
        LessonItem(
            title = dateString,
            desc = stringResource(id = R.string.absent_lesson_desc)
        )
        Spacer(modifier = Modifier.weight(1f))
        CommonButton(
            textResId = R.string.check_by_attendance,
            isAvailable = true,
            onClickNext = onClickCheckByAttendance
        )
    }
}

@Composable
private fun CompletedLessonItem(
    modifier: Modifier,
    itemState: LessonDateInfoUiState.CompletedLesson,
    focusDate: Long
) {
    val dateString = focusDate.toDisplayKrMonthDate()
    Column(modifier = modifier) {
        LessonItem(
            title = dateString,
            desc = stringResource(
                id = R.string.completed_lesson_desc,
                itemState.lessonIndex,
                itemState.lessonNumberOfProgress,
            )
        )
    }
}

@Composable
private fun LessonItem(
    title: String,
    @StringRes descRes: Int,
) {
    LessonItem(
        title = title,
        desc = stringResource(id = descRes)
    )
}

@Composable
private fun LessonItem(
    title: String,
    desc: String,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.common_btn_conner)))
            .background(GwasuwonConfigurationManager.colors.backgroundAlternative.toColor())
            .padding(
                dimensionResource(id = R.dimen.common_large_padding)
            )
    ) {
        Column {
            Text(
                text = title,
                style = GwasuwonTypography.Body1NormalBold.textStyle,
                color = GwasuwonConfigurationManager.colors.labelNormal.toColor()
            )
            SpaceHeight(height = 8)
            Text(
                text = desc,
                style = GwasuwonTypography.Label1NormalBold.textStyle,
                color = GwasuwonConfigurationManager.colors.labelNeutral.toColor()
            )
        }
    }
}