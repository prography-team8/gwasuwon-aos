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
import com.prography.ui.component.LoadingTransparentScreen
import com.prography.ui.component.SpaceHeight
import com.prography.ui.configuration.toColor
import com.prography.utils.date.DateUtils
import com.prography.utils.date.toDisplayKrMonthDate
import com.prography.utils.date.toKrTime
import kotlinx.collections.immutable.persistentListOf

/**
 * Created by MyeongKi.
 */
@Composable
fun LessonDetailRoute(
    viewModel: LessonDetailViewModel,
    isTeacher: Boolean,
) {
    val uiState = viewModel.machine.uiState.collectAsState().value
    /**
     * 캘린더는 매번 새로고침을 위하여 필요.
     */
    LaunchedEffect(true) {
        viewModel.machine.eventInvoker(LessonDetailActionEvent.Refresh())
    }
    LessonDetailScreen(
        uiState = uiState,
        isTeacher = isTeacher,
        intent = viewModel.machine.intentInvoker
    )
    LessonDetailDialogRoute(
        uiState.dialog,
        viewModel.machine.intentInvoker,
        viewModel.machine.eventInvoker
    )
    if(uiState.isLoading){
        LoadingTransparentScreen()
    }
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

        is LessonDetailDialog.NotifyLessonDeducted -> {
            ErrorDialog(
                titleResId = R.string.lesson_deducted_title,
                contentResId = R.string.lesson_deducted_title_desc,
                positiveResId = R.string.common_confirm,
                negativeResId = R.string.cancel,
                onClickPositive = {
                    intent(LessonDetailIntent.ClickNotifyLessonDeductedDialog)
                },
                onClickNegative = {
                    intent(LessonDetailIntent.ClickNotifyLessonDeductedDialog)
                },
                onClickBackground = {
                    intent(LessonDetailIntent.ClickNotifyLessonDeductedDialog)
                }
            )
        }

        else -> Unit
    }
}

@Composable
private fun LessonDetailScreen(
    uiState: LessonDetailUiState,
    isTeacher: Boolean,
    intent: (LessonDetailIntent) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(
                horizontal = dimensionResource(id = R.dimen.common_large_padding)
            )
            .fillMaxSize()
    ) {
        if (isTeacher) {
            LessonDetailTeacherToolbar(
                studentName = uiState.studentName,
                intent = intent
            )
        } else {
            LessonDetailStudentToolbar()
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
            isTeacher = isTeacher,
            intent = intent,
            hasStudent = uiState.hasStudent
        )
    }
}

@Composable
private fun LessonDetailStudentToolbar() {
    CommonToolbar(title = stringResource(id = R.string.lesson_schedule))
}

@Composable
private fun LessonDetailTeacherToolbar(
    studentName: String,
    intent: (LessonDetailIntent) -> Unit
) {
    CommonToolbar(
        title = stringResource(id = R.string.student_title, studentName),
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
}

@Composable
private fun LessonDateInfoRoute(
    modifier: Modifier,
    itemState: LessonDateInfoUiState,
    focusDate: Long,
    isTeacher: Boolean,
    hasStudent: Boolean,
    intent: (LessonDetailIntent) -> Unit
) {
    when (itemState) {
        is LessonDateInfoUiState.NoLesson -> {
            NoLessonItem(
                modifier = modifier,
                focusDate = focusDate,
                isTeacher = isTeacher,
                hasStudent = hasStudent,
                onClickRecognizeQr = {
                    intent(LessonDetailIntent.ClickRecognizeQr)
                },
                onClickInviteStudent = {
                    intent(LessonDetailIntent.ClickInviteStudent)
                }
            )
        }

        is LessonDateInfoUiState.ScheduleLesson -> {
            ScheduleLessonItem(
                modifier = modifier,
                focusDate = focusDate,
                isTeacher = isTeacher,
                hasStudent = hasStudent,
                onClickLessonCertification = {
                    intent(LessonDetailIntent.ClickLessonCertificationQr)
                },
                onClickRecognizeQr = {
                    intent(LessonDetailIntent.ClickRecognizeQr)
                },
                onClickInviteStudent = {
                    intent(LessonDetailIntent.ClickInviteStudent)
                }
            )
        }

        is LessonDateInfoUiState.AbsentLesson -> {
            AbsentLessonItem(
                modifier = modifier,
                focusDate = focusDate,
                isTeacher = isTeacher,
                hasStudent = hasStudent,
                onClickCheckByAttendance = {
                    intent(LessonDetailIntent.ClickCheckByAttendance)
                },
                onClickRecognizeQr = {
                    intent(LessonDetailIntent.ClickRecognizeQr)
                },
                onClickInviteStudent = {
                    intent(LessonDetailIntent.ClickInviteStudent)
                }
            )
        }

        is LessonDateInfoUiState.CompletedLesson -> {
            CompletedLessonItem(
                modifier = modifier,
                focusDate = focusDate,
                isTeacher = isTeacher,
                hasStudent = hasStudent,
                onClickRecognizeQr = {
                    intent(LessonDetailIntent.ClickRecognizeQr)
                },
                onClickInviteStudent = {
                    intent(LessonDetailIntent.ClickInviteStudent)
                }
            )
        }
    }
}

@Composable
private fun NoLessonItem(
    modifier: Modifier,
    focusDate: Long,
    isTeacher: Boolean,
    hasStudent: Boolean,
    onClickRecognizeQr: () -> Unit,
    onClickInviteStudent: () -> Unit

) {
    val dateString = focusDate.toDisplayKrMonthDate()
    Column(modifier = modifier) {
        LessonItem(
            title = dateString,
            descRes = R.string.no_lesson_desc
        )
        Spacer(modifier = Modifier.weight(1f))
        if (isTeacher.not()) {
            CommonButton(
                textResId = R.string.qr_recognition,
                isAvailable = true,
                onClickNext = onClickRecognizeQr
            )
        } else {
            if (hasStudent.not()) {
                CommonButton(
                    textResId = R.string.invite_student_qr,
                    isAvailable = true,
                    onClickNext = onClickInviteStudent
                )
            }
        }
    }
}

@Composable
private fun ScheduleLessonItem(
    modifier: Modifier,
    focusDate: Long,
    isTeacher: Boolean,
    hasStudent: Boolean,
    onClickLessonCertification: () -> Unit,
    onClickRecognizeQr: () -> Unit,
    onClickInviteStudent: () -> Unit

) {
    val dateString = focusDate.toDisplayKrMonthDate()
    Column(modifier = modifier) {
        LessonItem(
            title = dateString,
            descRes = R.string.schedule_lesson_desc
        )
        Spacer(modifier = Modifier.weight(1f))
        if (isTeacher) {
            if (focusDate == DateUtils.getCurrentLocalDate().toKrTime()) {
                CommonButton(
                    textResId = R.string.lesson_certification_qr,
                    isAvailable = true,
                    onClickNext = onClickLessonCertification
                )
            } else if (hasStudent.not()) {
                CommonButton(
                    textResId = R.string.invite_student_qr,
                    isAvailable = true,
                    onClickNext = onClickInviteStudent
                )
            }

        } else {
            CommonButton(
                textResId = R.string.qr_recognition,
                isAvailable = true,
                onClickNext = onClickRecognizeQr
            )
        }
    }
}

@Composable
private fun AbsentLessonItem(
    modifier: Modifier,
    focusDate: Long,
    isTeacher: Boolean,
    hasStudent: Boolean,
    onClickCheckByAttendance: () -> Unit,
    onClickRecognizeQr: () -> Unit,
    onClickInviteStudent: () -> Unit


) {
    val dateString = focusDate.toDisplayKrMonthDate()
    Column(modifier = modifier) {
        LessonItem(
            title = dateString,
            desc = stringResource(id = R.string.absent_lesson_desc)
        )
        Spacer(modifier = Modifier.weight(1f))
        if (isTeacher) {
            if (hasStudent) {
                CommonButton(
                    textResId = R.string.check_by_attendance,
                    isAvailable = true,
                    onClickNext = onClickCheckByAttendance
                )
            } else {
                CommonButton(
                    textResId = R.string.invite_student_qr,
                    isAvailable = true,
                    onClickNext = onClickInviteStudent
                )
            }

        } else {
            CommonButton(
                textResId = R.string.qr_recognition,
                isAvailable = true,
                onClickNext = onClickRecognizeQr
            )
        }
    }
}

@Composable
private fun CompletedLessonItem(
    modifier: Modifier,
    focusDate: Long,
    isTeacher: Boolean,
    hasStudent: Boolean,
    onClickRecognizeQr: () -> Unit,
    onClickInviteStudent: () -> Unit

) {
    val dateString = focusDate.toDisplayKrMonthDate()
    Column(modifier = modifier) {
        LessonItem(
            title = dateString,
            descRes = R.string.complete_lesson_desc
        )
        Spacer(modifier = Modifier.weight(1f))
        if (isTeacher.not()) {
            CommonButton(
                textResId = R.string.qr_recognition,
                isAvailable = true,
                onClickNext = onClickRecognizeQr
            )
        } else {
            if (hasStudent.not()) {
                CommonButton(
                    textResId = R.string.invite_student_qr,
                    isAvailable = true,
                    onClickNext = onClickInviteStudent
                )
            }
        }
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