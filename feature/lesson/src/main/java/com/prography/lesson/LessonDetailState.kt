package com.prography.lesson

import com.prography.usm.state.MachineInternalState
import com.prography.usm.state.UiState
import com.prography.utils.date.toKrMonthDateTime
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toImmutableSet

/**
 * Created by MyeongKi.
 */
data class LessonDetailMachineState(
    val studentName: String = "",
    val lessonNumberOfPostpone: Int = 0,
    val focusDate: Long = -1,
    val lessonDates: ImmutableSet<Long> = persistentSetOf(),
    val lessonAttendanceDates: List<Long> = listOf(),
    val lessonAbsentDates: List<LessonAbsentDate> = listOf(),
    val isLoading: Boolean = false,
    val hasStudent: Boolean = false,
    val dialog: LessonDetailDialog = LessonDetailDialog.None
) : MachineInternalState<LessonDetailUiState> {
    override fun toUiState(): LessonDetailUiState {
        val focusDateKr = focusDate.toKrMonthDateTime()
        val lessonAttendanceDatesKr = lessonAttendanceDates.asSequence().map { it.toKrMonthDateTime() }.toImmutableSet()
        val lessonAbsentDatesKr = lessonAbsentDates.asSequence().map { it.date.toKrMonthDateTime() }.toImmutableSet()
        val lessonDateInfoUiState = if (lessonDates.contains(focusDateKr)) {
            LessonDateInfoUiState.ScheduleLesson
        } else if (lessonAbsentDatesKr.contains(focusDateKr)) {
            LessonDateInfoUiState.AbsentLesson
        } else if (lessonAttendanceDatesKr.contains(focusDateKr)) {
            LessonDateInfoUiState.CompletedLesson
        } else {
            LessonDateInfoUiState.NoLesson

        }
        return LessonDetailUiState(
            studentName = studentName,
            focusDate = focusDateKr,
            lessonDateInfoUiState = lessonDateInfoUiState,
            lessonDates = lessonDates,
            lessonAttendanceDates = lessonAttendanceDatesKr,
            lessonAbsentDates = lessonAbsentDatesKr,
            isLoading = isLoading,
            dialog = dialog,
            hasStudent = hasStudent
        )
    }
}

data class LessonAbsentDate(
    val date: Long,
    val scheduleId: Long
)

data class LessonDetailUiState(
    val studentName: String,
    val focusDate: Long,
    val lessonDateInfoUiState: LessonDateInfoUiState,
    val lessonDates: ImmutableSet<Long>,
    val lessonAttendanceDates: ImmutableSet<Long>,
    val lessonAbsentDates: ImmutableSet<Long>,
    val isLoading: Boolean,
    val hasStudent: Boolean,
    val dialog: LessonDetailDialog
) : UiState

sealed interface LessonDateInfoUiState {
    data object NoLesson : LessonDateInfoUiState

    data object ScheduleLesson : LessonDateInfoUiState

    data object AbsentLesson : LessonDateInfoUiState

    data object CompletedLesson : LessonDateInfoUiState
}

sealed interface LessonDetailDialog {
    data object None : LessonDetailDialog

    data object DeleteLesson : LessonDetailDialog
    data object NotifyLessonDeducted : LessonDetailDialog
}