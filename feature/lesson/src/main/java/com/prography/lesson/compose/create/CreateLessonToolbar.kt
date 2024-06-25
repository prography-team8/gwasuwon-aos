package com.prography.lesson.compose.create

import androidx.compose.runtime.Composable
import com.prography.configuration.R
import com.prography.ui.CommonToolbar

/**
 * Created by MyeongKi.
 */

@Composable
internal fun CreateLessonHeader(
    onClickBack: () -> Unit
) {
    CommonToolbar(titleRes = R.string.add_lesson, onClickBack = onClickBack)
}