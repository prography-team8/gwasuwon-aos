package com.prography.lesson.compose

import androidx.activity.compose.BackHandler
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.prography.configuration.R
import com.prography.configuration.toColor
import com.prography.configuration.ui.GwasuwonConfigurationManager
import com.prography.lesson.CreateLessonActionEvent
import com.prography.lesson.CreateLessonIntent
import com.prography.lesson.CreateLessonUiState
import com.prography.lesson.CreateLessonViewModel
import com.prography.ui.CommonButton
import com.prography.ui.CommonToolbar
import com.prography.ui.GwasuwonTypography
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

/**
 * Created by MyeongKi.
 */
@Composable
fun CreateLessonRoute(
    viewModel: CreateLessonViewModel
) {
    when (val uiState = viewModel.machine.uiState.collectAsState().value) {
        is CreateLessonUiState.DefaultInfo -> {
            DefaultInfoScreen(
                uiState,
                viewModel.machine.eventInvoker,
                viewModel.machine.intentInvoker
            )
        }

        is CreateLessonUiState.AdditionalInfo -> {
            AdditionalInfoScreen(
                uiState,
                viewModel.machine.eventInvoker,
                viewModel.machine.intentInvoker
            )
        }

        is CreateLessonUiState.Complete -> {
            CompleteScreen(
                viewModel.machine.intentInvoker
            )
        }
    }
    BackHandler {
        viewModel.machine.intentInvoker(CreateLessonIntent.ClickBack)
    }
}

@Composable
private fun DefaultInfoScreen(
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
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = stringResource(id = R.string.default_info),
            color = GwasuwonConfigurationManager.colors.labelNormal.toColor(),
            style = GwasuwonTypography.Headline1Bold.textStyle
        )
        Spacer(modifier = Modifier.height(24.dp))
        LessonInfoInput(
            titleRes = R.string.default_info_student_name,
            hintRes = R.string.default_info_student_name_hint,
            inputText = uiState.studentName,
            onValueChange = { event(CreateLessonActionEvent.UpdateStudentName(it)) }
        )
        Spacer(modifier = Modifier.height(40.dp))
        LessonInfoInput(
            titleRes = R.string.default_info_school_year,
            hintRes = R.string.default_info_school_year_hint,
            inputText = uiState.schoolYear,
            onValueChange = { event(CreateLessonActionEvent.UpdateSchoolYear(it)) }
        )
        Spacer(modifier = Modifier.height(40.dp))
        LessonInfoInput(
            titleRes = R.string.default_info_memo,
            hintRes = R.string.default_info_memo_hint,
            inputText = uiState.memo,
            onValueChange = { event(CreateLessonActionEvent.UpdateMemo(it)) }
        )
        Spacer(modifier = Modifier.weight(1f))
        CommonButton(textResId = R.string.next, isAvailable = uiState.availableNextBtn) {
            event(CreateLessonActionEvent.GoToNextPage)
        }
    }
}

@Composable
private fun LessonInfoInput(
    @StringRes titleRes: Int,
    @StringRes hintRes: Int,
    inputText: String,
    onValueChange: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = titleRes),
            color = GwasuwonConfigurationManager.colors.labelNormal.toColor(),
            style = GwasuwonTypography.Caption1Bold.textStyle
        )
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .then(
                    if (inputText.isNotEmpty()) {
                        Modifier.border(
                            width = 1.dp,
                            color = GwasuwonConfigurationManager.colors.lineRegularNormal.toColor(),
                            shape = RoundedCornerShape(dimensionResource(id = R.dimen.sign_up_role_btn_conner))
                        )
                    } else {
                        Modifier.background(
                            GwasuwonConfigurationManager.colors.backgroundElevatedAlternative.toColor(),
                            RoundedCornerShape(dimensionResource(id = R.dimen.common_btn_conner))
                        )
                    }
                )
                .padding(vertical = 12.dp, horizontal = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = stringResource(id = hintRes),
                color = if (inputText.isEmpty()) GwasuwonConfigurationManager.colors.labelAssistive.toColor() else Color.Transparent,
                style = GwasuwonTypography.Body1NormalRegular.textStyle
            )
            BasicTextField(
                value = inputText,
                onValueChange = onValueChange,
                textStyle = GwasuwonTypography.Body1NormalRegular.textStyle.copy(
                    color = GwasuwonConfigurationManager.colors.labelNormal.toColor()
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun AdditionalInfoScreen(
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
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = stringResource(id = R.string.additional_info),
            color = GwasuwonConfigurationManager.colors.labelNormal.toColor(),
            style = GwasuwonTypography.Headline1Bold.textStyle
        )
        Spacer(modifier = Modifier.height(32.dp))
        var expanded by remember { mutableStateOf(false) }

        DropdownMenuComponent(
            expanded = expanded,
            selectedOptionText = "test",
            onDismissRequest = { expanded = false },
            onOptionSelected = {expanded = false },
            onExpandedChange = {expanded = !expanded },
            options = persistentListOf("1", "2", "3")
        )
    }
}

@Composable
fun DropdownMenuComponent(
    expanded: Boolean,
    selectedOptionText: String,
    onDismissRequest: () -> Unit,
    onOptionSelected: (Int) -> Unit,
    onExpandedChange: () -> Unit,
    options: ImmutableList<String>
) {
    Box {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    1.dp,
                    GwasuwonConfigurationManager.colors.lineRegularNormal.toColor(),
                    RoundedCornerShape(8.dp)
                )
                .padding(vertical = 12.dp, horizontal = 16.dp)
                .clickable { onExpandedChange() }
        ) {
            Text(
                text = selectedOptionText,
                style = GwasuwonTypography.Body1NormalRegular.textStyle
            )
            Spacer(modifier = Modifier.weight(1f))

        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onDismissRequest() },
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    1.dp,
                    GwasuwonConfigurationManager.colors.lineRegularNormal.toColor(),
                    RoundedCornerShape(8.dp)
                )
        ) {
            options.forEachIndexed { index, s ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = s,
                            style = GwasuwonTypography.Body1NormalRegular.textStyle
                        )
                    },
                    onClick = { onOptionSelected(index) }
                )
            }
        }
    }
}

@Composable
private fun CreateLessonHeader(
    onClickBack: () -> Unit
) {
    CommonToolbar(titleRes = R.string.add_lesson, onClickBack = onClickBack)
}

@Composable
private fun CompleteScreen(
    intent: (CreateLessonIntent) -> Unit
) {

}
