package com.prography.lesson.compose.create.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.prography.ui.R
import com.prography.lesson.CreateLessonActionEvent
import com.prography.lesson.CreateLessonIntent
import com.prography.lesson.CreateLessonUiState
import com.prography.lesson.compose.create.CreateLessonHeader
import com.prography.lesson.compose.create.CreateLessonInfoTitle
import com.prography.lesson.compose.create.LessonInfoInputItem
import com.prography.ui.component.CommonButton
import com.prography.ui.component.SpaceHeight

/**
 * Created by MyeongKi.
 */

@Composable
internal fun DefaultInfoScreen(
    uiState: CreateLessonUiState.DefaultInfo,
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
        CreateLessonInfoTitle(R.string.default_info)
        SpaceHeight(height = 24)
        LessonInfoInputItem(
            titleRes = R.string.default_info_student_name,
            hintRes = R.string.default_info_student_name_hint,
            inputText = uiState.studentName,
            onValueChange = { event(CreateLessonActionEvent.UpdateStudentName(it)) }
        )
        SpaceHeight(height = 40)
        LessonInfoInputItem(
            titleRes = R.string.default_info_school_year,
            hintRes = R.string.default_info_school_year_hint,
            inputText = uiState.schoolYear,
            onValueChange = { event(CreateLessonActionEvent.UpdateSchoolYear(it)) }
        )
        SpaceHeight(height = 40)
        LessonInfoInputItem(
            titleRes = R.string.default_info_memo,
            hintRes = R.string.default_info_memo_hint,
            inputText = uiState.memo,
            onValueChange = { event(CreateLessonActionEvent.UpdateMemo(it)) }
        )
        Spacer(modifier = Modifier.weight(1f))
        CommonButton(textResId = R.string.next, isAvailable = uiState.availableNextBtn) {
            intent(CreateLessonIntent.ClickNext)
        }
    }
}
