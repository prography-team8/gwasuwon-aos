package com.prography.utils.date

import kotlinx.datetime.Clock
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

/**
 * Created by MyeongKi.
 */
val KTS = TimeZone.of("Asia/Seoul")
object DateUtils {
    fun getCurrentLocalDateTime(): LocalDateTime {
        return getCurrentLocalDate().toLocalDateTime()
    }

    fun getCurrentLocalDate(): LocalDate {
        return Clock.System.now().toLocalDateTime(KTS).date
    }

    fun getCurrentDateTime(): Long {
        return Clock.System.now().toLocalDateTime(KTS).toTime()
    }

}
fun Long.toKtsTimeMillis(): Long {
    val instant = Instant.fromEpochMilliseconds(this)
    val localDateTime = instant.toLocalDateTime(KTS)
    return localDateTime.toTime()
}

fun Long.toUtcTimeMillis(): Long {
    val instant = Instant.fromEpochMilliseconds(this)
    val localDateTime = instant.toLocalDateTime(TimeZone.UTC)
    return localDateTime.toTime()
}

fun LocalDate.toTime(): Long {
    return LocalDateTime(date = this, time = LocalTime(0, 0)).toInstant(TimeZone.currentSystemDefault())
        .toEpochMilliseconds()
}

fun LocalDate.toLocalDateTime(): LocalDateTime {
    return LocalDateTime(date = this, time = LocalTime(0, 0))
}

fun LocalDateTime.toTime(): Long {
    return toInstant(KTS).toEpochMilliseconds()
}

fun Long.toLocalDateTime(): LocalDateTime {
    val instant = Instant.fromEpochMilliseconds(this)
    return instant.toLocalDateTime(KTS)
}


fun LocalDateTime.toDisplayYMDText(): String {
    return "${date.year}-${date.month.number.toDoubleDigit()}-${date.dayOfMonth.toDoubleDigit()}"
}

fun LocalDateTime.toDisplayYMDTText(): String {
    return "${date.year}.${date.month.number.toDoubleDigit()}.${date.dayOfMonth.toDoubleDigit()} " + "${time.hour.toDoubleDigit()}:${time.minute.toDoubleDigit()}"
}

fun Long.toDisplayKrMonthDate():String{
    val localDateTime = toLocalDateTime()
    return "${localDateTime.date.month.number}월 ${localDateTime.date.dayOfMonth}일 (${localDateTime.dayOfWeek.toKrString()})"
}

fun DayOfWeek.toKrString(): String {
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

private fun Int.toDoubleDigit(): String {
    return if (this < 10) {
        "0$this"
    } else {
        this.toString()
    }
}