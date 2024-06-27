package com.prography.lesson.compose.create

import androidx.compose.runtime.Composable
import com.prography.ui.R
import com.prography.ui.component.CommonToolbar

/**
 * Created by MyeongKi.
 */

@Composable
internal fun CreateLessonHeader(
    onClickBack: () -> Unit
) {
    CommonToolbar(titleRes = R.string.add_lesson, onClickBack = onClickBack)
}