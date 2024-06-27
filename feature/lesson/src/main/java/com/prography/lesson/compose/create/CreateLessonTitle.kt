package com.prography.lesson.compose.create

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.prography.ui.configuration.toColor
import com.prography.ui.component.GwasuwonConfigurationManager
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
@Composable
internal fun CreateLessonInfoSmallTitle(
    @StringRes textResId: Int
){
    Row {
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = stringResource(id = textResId),
            color = GwasuwonConfigurationManager.colors.labelNormal.toColor(),
            style = GwasuwonTypography.Caption1Bold.textStyle
        )
    }
}