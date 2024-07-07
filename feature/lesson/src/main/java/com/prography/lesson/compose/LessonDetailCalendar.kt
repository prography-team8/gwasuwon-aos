package com.prography.lesson.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.compose.CalendarLayoutInfo
import com.kizitonwose.calendar.compose.CalendarState
import com.kizitonwose.calendar.compose.ContentHeightMode
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.nextMonth
import com.kizitonwose.calendar.core.previousMonth
import com.kizitonwose.calendar.core.yearMonth
import com.prography.ui.GwasuwonTypography
import com.prography.ui.R
import com.prography.ui.component.GwasuwonConfigurationManager
import com.prography.ui.component.SpaceHeight
import com.prography.ui.component.SpaceWidth
import com.prography.ui.configuration.toColor
import com.prography.utils.date.JavaDateUtils
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.YearMonth
import java.time.ZoneId

/**
 * Created by MyeongKi.
 */

@Composable
internal fun LessonDetailCalendar(
    focusDate: Long,
    lessonDates: ImmutableSet<Long>,
    lessonAttendanceDates: ImmutableSet<Long>,
    lessonAbsentDates: ImmutableSet<Long>,
    onClickDay: (Long) -> Unit
) {
    val currentDate = remember {
        JavaDateUtils.getCurrentDate()
    }
    val startMonth = remember {
        currentDate.minusMonths(60).yearMonth
    }
    val endMonth = remember {
        currentDate.plusMonths(60).yearMonth
    }
    val daysOfWeek = remember {
        arrayOf(
            DayOfWeek.SUNDAY,
            DayOfWeek.MONDAY,
            DayOfWeek.TUESDAY,
            DayOfWeek.WEDNESDAY,
            DayOfWeek.THURSDAY,
            DayOfWeek.FRIDAY,
            DayOfWeek.SATURDAY
        )
    }
    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentDate.yearMonth,
        firstDayOfWeek = daysOfWeek.first(),
    )
    val coroutineScope = rememberCoroutineScope()
    val visibleMonth = rememberFirstMostVisibleMonth(state, viewportPercent = 90f)

    Column {
        SpaceHeight(16)
        SimpleCalendarTitle(
            modifier = Modifier,
            currentMonth = visibleMonth.yearMonth,
            goToPrevious = {
                coroutineScope.launch {
                    state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.previousMonth)
                }
            },
            goToNext = {
                coroutineScope.launch {
                    state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.nextMonth)
                }
            },
        )
        SpaceHeight(16)
        HorizontalCalendar(
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp),
            state = state,
            contentHeightMode = ContentHeightMode.Fill,
            dayContent = { value ->
                val zonedDateTime = value.date.atStartOfDay(ZoneId.of("Asia/Seoul"))
                val epochMilli = zonedDateTime.toInstant().toEpochMilli()
                val type = when {
                    focusDate == epochMilli -> LessonDayType.FOCUS
                    lessonAbsentDates.contains(epochMilli) -> LessonDayType.ABSENT
                    lessonAttendanceDates.contains(epochMilli) -> LessonDayType.COMPLETE
                    lessonDates.contains(epochMilli) -> LessonDayType.SCHEDULE
                    else -> LessonDayType.NONE
                }
                Day(value, type) {
                    onClickDay(epochMilli)
                }
            },
            monthHeader = {
                MonthHeader(daysOfWeek = daysOfWeek)
            }
        )
    }
}

@Composable
fun SimpleCalendarTitle(
    modifier: Modifier,
    currentMonth: YearMonth,
    goToPrevious: () -> Unit,
    goToNext: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Image(
            modifier = Modifier
                .size(20.dp)
                .clickable(onClick = goToPrevious),
            painter = painterResource(id = R.drawable.icon_left),
            contentDescription = "Previous",
        )
        SpaceWidth(width = 16)
        Text(
            text = currentMonth.displayText(),
            textAlign = TextAlign.Center,
            style = GwasuwonTypography.Heading2Bold.textStyle,
            color = GwasuwonConfigurationManager.colors.labelNormal.toColor()
        )
        SpaceWidth(width = 16)
        Image(
            modifier = Modifier
                .size(20.dp)
                .clickable(onClick = goToNext),
            painter = painterResource(id = R.drawable.icon_right),
            contentDescription = "Next",
        )
    }
}

private fun YearMonth.displayText(): String {
    return "${monthValue}월"
}

@Composable
private fun MonthHeader(daysOfWeek: Array<DayOfWeek>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
    ) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = dayOfWeek.displayText(),
                style = GwasuwonTypography.Caption2Bold.textStyle,
                color = GwasuwonConfigurationManager.colors.labelNormal.toColor()
            )
        }
    }
}

private fun DayOfWeek.displayText(): String {
    return when (this) {
        DayOfWeek.MONDAY -> "월"
        DayOfWeek.TUESDAY -> "화"
        DayOfWeek.WEDNESDAY -> "수"
        DayOfWeek.THURSDAY -> "목"
        DayOfWeek.FRIDAY -> "금"
        DayOfWeek.SATURDAY -> "토"
        DayOfWeek.SUNDAY -> "일"
    }
}

@Composable
private fun Day(
    day: CalendarDay,
    type: LessonDayType,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clickable(
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = when (type) {
                LessonDayType.FOCUS -> {
                    Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(
                            GwasuwonConfigurationManager.colors.fillStrong.toColor()
                        )
                }

                LessonDayType.COMPLETE -> {
                    Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(
                            GwasuwonConfigurationManager.colors.primaryNormal.toColor()
                        )
                }

                LessonDayType.ABSENT -> {
                    Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(
                            GwasuwonConfigurationManager.colors.statusNegative.toColor()
                        )
                }

                LessonDayType.SCHEDULE -> {
                    Modifier
                        .border(
                            width = 1.dp,
                            color = GwasuwonConfigurationManager.colors.primaryNormal.toColor(),
                            shape = RoundedCornerShape(4.dp)
                        )
                }

                else -> Modifier
            }.size(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = day.date.dayOfMonth.toString(),
                style = if (type == LessonDayType.FOCUS) {
                    GwasuwonTypography.Caption2Bold.textStyle
                } else {
                    GwasuwonTypography.Caption2Regular.textStyle
                },
                color = if (day.position == DayPosition.OutDate || day.position == DayPosition.InDate) {
                    GwasuwonConfigurationManager.colors.labelDisable.toColor()
                } else {
                    when (type) {
                        LessonDayType.FOCUS, LessonDayType.NONE -> GwasuwonConfigurationManager.colors.labelNormal.toColor()
                        LessonDayType.ABSENT, LessonDayType.COMPLETE -> GwasuwonConfigurationManager.colors.staticWhite.toColor()
                        LessonDayType.SCHEDULE -> GwasuwonConfigurationManager.colors.primaryNormal.toColor()
                    }
                }
            )
        }

    }
}

private enum class LessonDayType {
    FOCUS,
    COMPLETE,
    ABSENT,
    SCHEDULE,
    NONE;
}

@Composable
fun rememberFirstMostVisibleMonth(
    state: CalendarState,
    viewportPercent: Float = 50f,
): CalendarMonth {
    val visibleMonth = remember(state) { mutableStateOf(state.firstVisibleMonth) }
    LaunchedEffect(state) {
        snapshotFlow { state.layoutInfo.firstMostVisibleMonth(viewportPercent) }
            .filterNotNull()
            .collect { month -> visibleMonth.value = month }
    }
    return visibleMonth.value
}

private fun CalendarLayoutInfo.firstMostVisibleMonth(viewportPercent: Float = 50f): CalendarMonth? {
    return if (visibleMonthsInfo.isEmpty()) {
        null
    } else {
        val viewportSize = (viewportEndOffset + viewportStartOffset) * viewportPercent / 100f
        visibleMonthsInfo.firstOrNull { itemInfo ->
            if (itemInfo.offset < 0) {
                itemInfo.offset + itemInfo.size >= viewportSize
            } else {
                itemInfo.size - itemInfo.offset >= viewportSize
            }
        }?.month
    }
}