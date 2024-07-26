package com.prography.lesson.compose.create.screen

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.text.input.KeyboardType
import com.prography.domain.lesson.model.LessonDuration
import com.prography.domain.lesson.model.LessonSubject
import com.prography.lesson.AdditionalInfo
import com.prography.lesson.AdditionalInfoIntent
import com.prography.lesson.CreateLessonIntent
import com.prography.lesson.compose.LessonInfoInputItem
import com.prography.lesson.compose.create.CreateLessonInfoSmallExclamationTitle
import com.prography.lesson.compose.create.CreateLessonInfoSmallTitle
import com.prography.lesson.compose.create.CreateLessonInfoTitle
import com.prography.lesson.utils.getLessonDurationStringRes
import com.prography.lesson.utils.getLessonSubjectStringRes
import com.prography.ui.R
import com.prography.ui.component.CommonButton
import com.prography.ui.component.DatePickerButton
import com.prography.ui.component.DropdownMenuComponent
import com.prography.ui.component.SpaceHeight
import kotlinx.collections.immutable.toPersistentList

/**
 * Created by MyeongKi.
 */
@Composable
fun AdditionalInfoList(
    scrollState: ScrollState,
    uiState: AdditionalInfo,
    focusManager: FocusManager,
    bottomBtnContent: @Composable () -> Unit,
    intent: (AdditionalInfoIntent) -> Unit,
) {
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
                    intent(AdditionalInfoIntent.ClickLessonSubject(it))
                }
            }
        )
        SpaceHeight(height = 32)
        CreateLessonInfoTitle(R.string.lesson_schedule)
        SpaceHeight(height = 32)
        CreateLessonInfoSmallTitle(textResId = R.string.lesson_start_date)
        SpaceHeight(height = 8)

        DatePickerButton(
            selectedDate = uiState.lessonStartDateTime ?: -1,
            onClickConfirm = {
                intent(AdditionalInfoIntent.ClickLessonDate(it))
            },
            onExpandPicker = {
                focusManager.clearFocus()
            }
        )
        SpaceHeight(height = 24)
        CreateLessonInfoSmallTitle(textResId = R.string.lesson_duration_title)
        SpaceHeight(height = 8)
        val lessonDuration = remember { LessonDuration.entries.toList() }
        DropdownMenuComponent(
            defaultOptionTextResId = R.string.lesson_duration,
            selectedOptionTextResId = uiState.lessonDuration?.getLessonDurationStringRes(),
            optionResIds = lessonDuration.map { it.getLessonDurationStringRes() }.toPersistentList(),
            onOptionSelected = { index ->
                lessonDuration.getOrNull(index)?.let {
                    intent(AdditionalInfoIntent.ClickLessonDuration(it))
                }
            }
        )
        SpaceHeight(height = 24)
        CreateLessonInfoSmallTitle(textResId = R.string.lesson_schedule_day)
        SpaceHeight(height = 12)
        SelectLessonDay(
            uiState.lessonDay
        ) {
            intent(AdditionalInfoIntent.ClickLessonDay(it))
        }
        SpaceHeight(height = 24)
        LessonInfoInputItem(
            titleRes = R.string.lesson_progress_time,
            hintRes = R.string.lesson_progress_time_hint,
            inputText = uiState.lessonNumberOfProgress?.toString() ?: "",
            keyboardType = KeyboardType.Decimal,
            onValueChange = {
                intent(
                    AdditionalInfoIntent.InputLessonNumberOfProgress(
                        it.toIntOrNull() ?: 1
                    )
                )
            }
        )
        SpaceHeight(height = 24)
        CreateLessonInfoSmallExclamationTitle(textResId = R.string.postpone_lesson_title) {
            intent(AdditionalInfoIntent.ClickPostponeInformationIcon)
        }
        SpaceHeight(height = 8)
        SelectLessonNumberOfPostpone(
            uiState.lessonNumberOfPostpone
        ) {
            intent(
                AdditionalInfoIntent.ClickLessonNumberOfPostpone(
                    it
                )
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        bottomBtnContent()

    }
}