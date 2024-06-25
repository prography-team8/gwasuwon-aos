package com.prography.lesson.compose.create.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.prography.configuration.R
import com.prography.configuration.toColor
import com.prography.configuration.ui.GwasuwonConfigurationManager
import com.prography.domain.lesson.model.LessonDuration
import com.prography.domain.lesson.model.LessonSubject
import com.prography.lesson.CreateLessonActionEvent
import com.prography.lesson.CreateLessonIntent
import com.prography.lesson.CreateLessonUiState
import com.prography.lesson.compose.create.CreateLessonHeader
import com.prography.lesson.compose.create.CreateLessonInfoTitle
import com.prography.lesson.utils.getLessonDurationStringRes
import com.prography.lesson.utils.getLessonSubjectStringRes
import com.prography.ui.DropdownMenuComponent
import com.prography.ui.GwasuwonTypography
import com.prography.ui.SpaceHeight
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
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        CreateLessonHeader {
            intent(CreateLessonIntent.ClickBack)
        }
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
                    event(CreateLessonActionEvent.UpdateLessonSubject(it))
                }
            }
        )
        SpaceHeight(height = 48)
        CreateLessonInfoTitle(R.string.lesson_schedule)
        SpaceHeight(height = 32)
        Row {
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(id = R.string.lesson_schedule),
                color = GwasuwonConfigurationManager.colors.labelNormal.toColor(),
                style = GwasuwonTypography.Caption1Bold.textStyle
            )
        }
        SpaceHeight(height = 8)
        val lessonDuration = remember { LessonDuration.entries.toList() }
        DropdownMenuComponent(
            defaultOptionTextResId = R.string.lesson_duration,
            selectedOptionTextResId = uiState.lessonDuration?.getLessonDurationStringRes(),
            optionResIds = lessonDuration.map { it.getLessonDurationStringRes() }.toPersistentList(),
            onOptionSelected = { index ->
                lessonDuration.getOrNull(index)?.let {
                    event(CreateLessonActionEvent.UpdateLessonDuration(it))
                }
            }
        )
    }
}


