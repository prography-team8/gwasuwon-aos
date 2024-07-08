package com.prography.lesson.compose.create.screen

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.prography.domain.lesson.model.LessonDay
import com.prography.domain.lesson.model.LessonDuration
import com.prography.domain.lesson.model.LessonSubject
import com.prography.lesson.CreateLessonActionEvent
import com.prography.lesson.CreateLessonIntent
import com.prography.lesson.CreateLessonUiState
import com.prography.lesson.compose.create.CreateLessonHeader
import com.prography.lesson.compose.create.CreateLessonInfoSmallTitle
import com.prography.lesson.compose.create.CreateLessonInfoTitle
import com.prography.lesson.compose.LessonInfoInputItem
import com.prography.lesson.utils.getLessonDayStringRes
import com.prography.lesson.utils.getLessonDurationStringRes
import com.prography.lesson.utils.getLessonSubjectStringRes
import com.prography.ui.GwasuwonTypography
import com.prography.ui.R
import com.prography.ui.component.CommonButton
import com.prography.ui.component.DatePickerButton
import com.prography.ui.component.DropdownMenuComponent
import com.prography.ui.component.GwasuwonConfigurationManager
import com.prography.ui.component.SpaceHeight
import com.prography.ui.configuration.toColor
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentList

/**
 * Created by MyeongKi.
 */

@Composable
internal fun AdditionalInfoScreen(
    uiState: CreateLessonUiState.AdditionalInfo,
    event: (CreateLessonActionEvent) -> Unit,
    intent: (CreateLessonIntent) -> Unit
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .padding(
                horizontal = dimensionResource(id = R.dimen.common_large_padding)
            )
            .fillMaxSize()
    ) {
        CreateLessonHeader {
            intent(CreateLessonIntent.ClickBack)
        }
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {

            SpaceHeight(height = 24)
            CreateLessonInfoTitle(R.string.additional_info)
            SpaceHeight(height = 32)
            val lessonSubjects = remember { LessonSubject.entries.asSequence().sortedBy { it.index }.toList() }
            DropdownMenuComponent(
                defaultOptionTextResId = R.string.select_subject,
                selectedOptionTextResId = uiState.lessonSubject?.getLessonSubjectStringRes(),
                optionResIds = lessonSubjects.map { it.getLessonSubjectStringRes() }.toPersistentList(),
                onOptionSelected = { index ->
                    lessonSubjects.getOrNull(index)?.let {
                        intent(CreateLessonIntent.ClickLessonSubject(it))
                    }
                }
            )
            SpaceHeight(height = 32)
            CreateLessonInfoTitle(R.string.lesson_schedule)
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
                        intent(CreateLessonIntent.ClickLessonDuration(it))
                    }
                }
            )
            SpaceHeight(height = 24)
            CreateLessonInfoSmallTitle(textResId = R.string.lesson_schedule_day)
            SpaceHeight(height = 12)
            SelectLessonDay(
                uiState.lessonDay
            ) {
                intent(CreateLessonIntent.ClickLessonDay(it))
            }
            SpaceHeight(height = 24)
            LessonInfoInputItem(
                titleRes = R.string.lesson_progress_time,
                hintRes = R.string.lesson_progress_time_hint,
                inputText = uiState.lessonNumberOfProgress?.toString() ?: "",
                keyboardType = KeyboardType.Number,
                onValueChange = {
                    event(
                        CreateLessonActionEvent.UpdateLessonNumberOfProgress(
                            it.toIntOrNull() ?: 1
                        )
                    )
                }
            )
            SpaceHeight(height = 24)
            CreateLessonInfoSmallTitle(textResId = R.string.lesson_start_date)
            SpaceHeight(height = 8)

            DatePickerButton(
                selectedDate = uiState.lessonStartDateTime ?: -1,
                onClickConfirm = {
                    intent(CreateLessonIntent.ClickLessonDate(it))
                },
                onExpandPicker = {
                    focusManager.clearFocus()
                }
            )
            SpaceHeight(height = 24)
            CreateLessonInfoSmallTitle(textResId = R.string.postpone_lesson_title)
            SpaceHeight(height = 8)
            SelectLessonNumberOfPostpone(
                uiState.lessonNumberOfPostpone
            ) {
                intent(
                    CreateLessonIntent.ClickLessonNumberOfPostpone(
                        it
                    )
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            CommonButton(textResId = R.string.create_lesson, isAvailable = uiState.availableNextBtn) {
                intent(CreateLessonIntent.ClickCreateLesson)
            }
        }
    }
}

@Composable
internal fun SelectLessonNumberOfPostpone(
    lessonNumberOfPostpone: Int?,
    onLessonNumberOfPostponeSelected: (Int) -> Unit
) {
    val lessonNumberOfPostponeTarget = remember { listOf(0, 1, 2) }
    DropdownMenuComponent(
        defaultOptionText = stringResource(id = R.string.postpone_lesson_title),
        selectedOptionText = lessonNumberOfPostpone?.let { stringResource(id = R.string.postpone_lesson_target, it) },
        option = lessonNumberOfPostponeTarget.map { stringResource(id = R.string.postpone_lesson_target, it) }.toImmutableList(),
        onOptionSelected = onLessonNumberOfPostponeSelected
    )
}

@Composable
internal fun SelectLessonDay(
    lessonDaySelected: ImmutableSet<LessonDay>,
    onLessonDaySelected: (LessonDay) -> Unit
) {
    val lessonDays = remember { LessonDay.entries.toList() }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        lessonDays
            .map { it.getLessonDayStringRes() }
            .forEachIndexed { index, textId ->
                LessonDayItem(
                    textResId = textId,
                    selected = lessonDaySelected.contains(lessonDays[index]),
                    onClickItem = {
                        onLessonDaySelected(lessonDays[index])
                    }
                )
            }
    }
}

@Composable
private fun LessonDayItem(
    @StringRes textResId: Int,
    selected: Boolean,
    onClickItem: () -> Unit
) {
    Box(
        modifier = Modifier
            .wrapContentSize()
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.common_btn_conner)))
            .background(
                if (selected) {
                    GwasuwonConfigurationManager.colors.primaryNormal.toColor()
                } else {
                    GwasuwonConfigurationManager.colors.backgroundElevatedAlternative.toColor()
                }
            )
            .padding(dimensionResource(id = R.dimen.common_padding))
            .clickable(onClick = onClickItem)
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = stringResource(id = textResId),
            color = if (selected) {
                GwasuwonConfigurationManager.colors.staticWhite.toColor()
            } else {
                GwasuwonConfigurationManager.colors.labelNormal.toColor()
            },
            style = GwasuwonTypography.Body1NormalBold.textStyle
        )
    }
}

