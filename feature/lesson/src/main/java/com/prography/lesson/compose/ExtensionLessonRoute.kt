package com.prography.lesson.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import com.prography.lesson.ExtensionLessonDialog
import com.prography.lesson.ExtensionLessonIntent
import com.prography.lesson.ExtensionLessonUiState
import com.prography.lesson.ExtensionLessonViewModel
import com.prography.lesson.compose.create.screen.AdditionalInfoList
import com.prography.ui.R
import com.prography.ui.component.CommonButton
import com.prography.ui.component.CommonDialog
import com.prography.ui.component.CommonToolbar
import com.prography.ui.component.LoadingTransparentScreen

/**
 * Created by MyeongKi.
 */

@Composable
fun ExtensionLessonRoute(
    viewModel: ExtensionLessonViewModel
) {
    val uiState = viewModel.machine.uiState.collectAsState().value
    ExtensionLessonScreen(
        uiState = uiState,
        intent = viewModel.machine.intentInvoker
    )
}

@Composable
internal fun ExtensionLessonScreen(
    uiState: ExtensionLessonUiState,
    intent: (ExtensionLessonIntent) -> Unit
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .padding(
                horizontal = dimensionResource(id = R.dimen.common_large_padding)
            )
            .fillMaxSize()
    ) {
        CommonToolbar(
            titleRes = R.string.extension_lesson_title,
            onClickBack = {
                intent(ExtensionLessonIntent.ClickBack)
            }
        )

        val scrollState = rememberScrollState()

        AdditionalInfoList(
            uiState = uiState.additionalInfo,
            scrollState = scrollState,
            focusManager = focusManager,
            bottomBtnContent = {
                CommonButton(textResId = R.string.extension_lesson, isAvailable = uiState.availableExtensionBtn) {
                    intent(ExtensionLessonIntent.ClickExtensionLesson)
                }
            }
        ) {
            intent(ExtensionLessonIntent.AdditionalInfo(it))
        }
    }
    if (uiState.dialog == ExtensionLessonDialog.PostponeInformation) {
        CommonDialog(
            titleResId = R.string.lesson_postpone_title,
            contentResId = R.string.lesson_postpone_content,
            positiveResId = R.string.common_confirm,
            onClickPositive = {
                intent(ExtensionLessonIntent.ClickDialog)
            },
            onClickBackground = {
                intent(ExtensionLessonIntent.ClickDialog)
            }
        )
    }
    if (uiState.isLoading) {
        LoadingTransparentScreen()
    }
}
