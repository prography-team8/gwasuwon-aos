package com.prography.lesson.compose.create

import androidx.annotation.StringRes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.prography.configuration.toColor
import com.prography.configuration.ui.GwasuwonConfigurationManager
import com.prography.ui.GwasuwonTypography

/**
 * Created by MyeongKi.
 */

@Composable
internal fun CreateLessonInfoTitle(
    @StringRes textResId: Int
) {
    Text(
        text = stringResource(id = textResId),
        color = GwasuwonConfigurationManager.colors.labelNormal.toColor(),
        style = GwasuwonTypography.Headline1Bold.textStyle
    )
}
