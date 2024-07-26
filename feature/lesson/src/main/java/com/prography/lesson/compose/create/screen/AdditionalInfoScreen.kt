package com.prography.lesson.compose.create.screen

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.prography.domain.lesson.model.LessonDay
import com.prography.lesson.CreateLessonDialog
import com.prography.lesson.CreateLessonIntent
import com.prography.lesson.CreateLessonIntent.ClickBack.toCreateIntent
import com.prography.lesson.CreateLessonUiState
import com.prography.lesson.compose.create.CreateLessonHeader
import com.prography.lesson.utils.getLessonDayStringRes
import com.prography.ui.GwasuwonTypography
import com.prography.ui.R
import com.prography.ui.component.CommonButton
import com.prography.ui.component.CommonDialog
import com.prography.ui.component.CommonDialogIntent
import com.prography.ui.component.CommonDialogRoute
import com.prography.ui.component.DropdownMenuComponent
import com.prography.ui.component.GwasuwonConfigurationManager
import com.prography.ui.component.LoadingTransparentScreen
import com.prography.ui.configuration.toColor
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.toImmutableList

/**
 * Created by MyeongKi.
 */

@Composable
internal fun AdditionalInfoScreen(
    uiState: CreateLessonUiState.CreateAdditionalInfo,
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

        AdditionalInfoList(
            uiState = uiState.additionalInfo,
            scrollState = scrollState,
            focusManager = focusManager,
            bottomBtnContent = {
                CommonButton(textResId = R.string.create_lesson, isAvailable = uiState.availableNextBtn) {
                    intent(CreateLessonIntent.ClickCreateLesson)
                }
            }
        ) {
            intent(CreateLessonIntent.AdditionalInfo(it))
        }
    }
    when(uiState.dialog){
        is CreateLessonDialog.PostponeInformation -> {
            CommonDialog(
                titleResId = R.string.lesson_postpone_title,
                contentResId = R.string.lesson_postpone_content,
                positiveResId = R.string.common_confirm,
                onClickPositive = {
                    intent(CommonDialogIntent.ClickConfirm.toCreateIntent())
                },
                onClickBackground = {
                    intent(CommonDialogIntent.ClickBackground.toCreateIntent())
                }
            )
        }
        is CreateLessonDialog.CreateLessonCommonDialog->{
            CommonDialogRoute(dialog = uiState.dialog.state) {
                intent(it.toCreateIntent())
            }
        }
    }
    if (uiState.isLoading) {
        LoadingTransparentScreen()
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

