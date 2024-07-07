package com.prography.lesson.compose

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.prography.ui.GwasuwonTypography
import com.prography.ui.R
import com.prography.ui.component.GwasuwonConfigurationManager
import com.prography.ui.component.SpaceHeight
import com.prography.ui.component.SpaceWidth
import com.prography.ui.configuration.toColor

/**
 * Created by MyeongKi.
 */
@Composable
internal fun LessonDetailCalendarDesc() {
    Column {
        Row(
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            Box(modifier = Modifier
                .size(12.dp)
                .border(
                    width = 1.dp,
                    color = GwasuwonConfigurationManager.colors.primaryNormal.toColor(),
                    shape = RoundedCornerShape(2.dp)
                )
            )
            SpaceWidth(width = 4)
            LessonDetailCalendarDescText(R.string.calendar_schedule_lesson_desc)
        }
        SpaceHeight(height = 8)
        Row(
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            Box(modifier = Modifier
                .size(12.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(
                    GwasuwonConfigurationManager.colors.primaryNormal.toColor()
                )
            )
            SpaceWidth(width = 4)
            LessonDetailCalendarDescText(R.string.calendar_attendance_lesson_desc)
        }
        SpaceHeight(height = 8)
        Row(
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            Box(modifier = Modifier
                .size(12.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(
                    GwasuwonConfigurationManager.colors.statusNegative.toColor()
                )
            )
            SpaceWidth(width = 4)
            LessonDetailCalendarDescText(R.string.calendar_absent_lesson_desc)
        }
    }
}

@Composable
private fun LessonDetailCalendarDescText(
    @StringRes textResId: Int
) {
    Text(
        text = stringResource(id = textResId),
        style = GwasuwonTypography.Caption2Regular.textStyle,
        color = GwasuwonConfigurationManager.colors.labelNormal.toColor()
    )
}
