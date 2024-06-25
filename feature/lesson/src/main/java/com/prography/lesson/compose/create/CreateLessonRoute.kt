package com.prography.lesson.compose.create

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.prography.lesson.CreateLessonIntent
import com.prography.lesson.CreateLessonUiState
import com.prography.lesson.CreateLessonViewModel
import com.prography.lesson.compose.create.screen.AdditionalInfoScreen
import com.prography.lesson.compose.create.screen.CompleteScreen
import com.prography.lesson.compose.create.screen.DefaultInfoScreen

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
