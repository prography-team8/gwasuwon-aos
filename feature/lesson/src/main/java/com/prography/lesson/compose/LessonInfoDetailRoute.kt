package com.prography.lesson.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.prography.domain.lesson.model.LessonDuration
import com.prography.domain.lesson.model.LessonSubject
import com.prography.lesson.LessonInfoDetailActionEvent
import com.prography.lesson.LessonInfoDetailIntent
import com.prography.lesson.LessonInfoDetailUiState
import com.prography.lesson.LessonInfoDetailViewModel
import com.prography.lesson.compose.create.CreateLessonInfoSmallTitle
import com.prography.lesson.compose.create.screen.SelectLessonDay
import com.prography.lesson.compose.create.screen.SelectLessonNumberOfPostpone
import com.prography.lesson.utils.getLessonDurationStringRes
import com.prography.lesson.utils.getLessonSubjectStringRes
import com.prography.ui.GwasuwonTypography
import com.prography.ui.R
import com.prography.ui.component.CommonToolbar
import com.prography.ui.component.DatePickerButton
import com.prography.ui.component.DropdownMenuComponent
import com.prography.ui.component.GwasuwonConfigurationManager
import com.prography.ui.component.SpaceHeight
import com.prography.ui.configuration.toColor
import kotlinx.collections.immutable.toPersistentList

/**
 * Created by MyeongKi.
 */
@Composable
fun LessonInfoDetailRoute(
    viewModel: LessonInfoDetailViewModel
) {
    val uiState = viewModel.machine.uiState.collectAsState()
    LaunchedEffect(true) {
        viewModel.machine.eventInvoker(LessonInfoDetailActionEvent.Refresh)
    }
    LessonInfoDetailScreen(
        uiState = uiState.value,
        event = viewModel.machine.eventInvoker,
        intent = viewModel.machine.intentInvoker
    )
}

@Composable
private fun LessonInfoDetailScreen(
    uiState: LessonInfoDetailUiState,
    event: (LessonInfoDetailActionEvent) -> Unit,
    intent: (LessonInfoDetailIntent) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        LessonInfoToolbar(
            isVisibleUpdateBtn = uiState.isVisibleUpdateBtn,
            onClickUpdate = { intent(LessonInfoDetailIntent.ClickUpdateLesson) },
            onClickBack = { intent(LessonInfoDetailIntent.ClickBack) }
        )
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            LessonInfoDetailDesc()
            SpaceHeight(height = 48)
            LessonInfoInputItem(
                titleRes = R.string.default_info_student_name,
                hintRes = R.string.default_info_student_name_hint,
                inputText = uiState.studentName,
                onValueChange = { event(LessonInfoDetailActionEvent.UpdateStudentName(it)) }
            )
            SpaceHeight(height = 32)
            LessonInfoInputItem(
                titleRes = R.string.default_info_school_year,
                hintRes = R.string.default_info_school_year_hint,
                inputText = uiState.schoolYear,
                onValueChange = { event(LessonInfoDetailActionEvent.UpdateSchoolYear(it)) }
            )
            SpaceHeight(height = 32)
            val lessonSubjects = remember { LessonSubject.entries.asSequence().sortedBy { it.index }.toList() }
            DropdownMenuComponent(
                defaultOptionTextResId = R.string.select_subject,
                selectedOptionTextResId = uiState.lessonSubject?.getLessonSubjectStringRes(),
                optionResIds = lessonSubjects.map { it.getLessonSubjectStringRes() }.toPersistentList(),
                onOptionSelected = { index ->
                    lessonSubjects.getOrNull(index)?.let {
                        intent(LessonInfoDetailIntent.ClickLessonSubject(it))
                    }
                }
            )
            SpaceHeight(height = 32)
            CreateLessonInfoSmallTitle(textResId = R.string.lesson_duration_title)
            SpaceHeight(height = 8)
            val lessonDuration = remember { LessonDuration.entries.toList() }
            DropdownMenuComponent(
                defaultOptionTextResId = R.string.lesson_duration,
                selectedOptionTextResId = uiState.lessonDuration?.getLessonDurationStringRes(),
                optionResIds = lessonDuration.map { it.getLessonDurationStringRes() }.toPersistentList(),
                onOptionSelected = { index ->
                    lessonDuration.getOrNull(index)?.let {
                        intent(LessonInfoDetailIntent.ClickLessonDuration(it))
                    }
                }
            )
            SpaceHeight(height = 32)
            CreateLessonInfoSmallTitle(textResId = R.string.lesson_schedule_day)
            SpaceHeight(height = 12)
            SelectLessonDay(
                uiState.lessonDay
            ) {
                intent(LessonInfoDetailIntent.ClickLessonDay(it))
            }
            SpaceHeight(height = 32)
            LessonInfoInputItem(
                titleRes = R.string.lesson_progress_time,
                hintRes = R.string.lesson_progress_time_hint,
                inputText = uiState.lessonNumberOfProgress?.toString() ?: "",
                keyboardType = KeyboardType.Number,
                onValueChange = {
                    event(
                        LessonInfoDetailActionEvent.UpdateLessonNumberOfProgress(
                            it.toIntOrNull() ?: 1
                        )
                    )
                }
            )
            SpaceHeight(height = 32)
            CreateLessonInfoSmallTitle(textResId = R.string.lesson_start_date)
            SpaceHeight(height = 8)

            DatePickerButton(
                onClickConfirm = {
                    intent(LessonInfoDetailIntent.ClickLessonDate(it))
                }
            )
            SpaceHeight(height = 32)
            CreateLessonInfoSmallTitle(textResId = R.string.postpone_lesson_title)
            SpaceHeight(height = 8)
            SelectLessonNumberOfPostpone(
                uiState.lessonNumberOfPostpone
            ) {
                intent(
                    LessonInfoDetailIntent.ClickLessonNumberOfPostpone(
                        it
                    )
                )
            }
        }
    }
}

@Composable
private fun LessonInfoToolbar(
    isVisibleUpdateBtn: Boolean,
    onClickUpdate: () -> Unit,
    onClickBack: () -> Unit
) {
    CommonToolbar(
        titleRes = R.string.lesson_info_detail_title,
        rightTextRes = R.string.update,
        isVisibleRight = isVisibleUpdateBtn,
        onClickRight = onClickUpdate,
        onClickBack = onClickBack
    )
}

@Composable
private fun LessonInfoDetailDesc() {
    Text(
        text = stringResource(id = R.string.lesson_info_detail_desc),
        style = GwasuwonTypography.Caption1Regular.textStyle,
        color = GwasuwonConfigurationManager.colors.labelNeutral.toColor()
    )
}