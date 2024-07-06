package com.prography.lesson

import com.prography.usm.state.MachineInternalState
import com.prography.usm.state.UiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.toImmutableSet

/**
 * Created by MyeongKi.
 */
data class LessonDetailMachineState(
    val studentName: String,
    val lessonNumberOfProgress: Int,
    val focusDate: Long,
    val lessonDates: ImmutableSet<Long>,
    val lessonAttendanceDates: ImmutableList<Long>,
    val lessonAbsentDates: ImmutableSet<Long>,
) : MachineInternalState<LessonDetailUiState> {
    override fun toUiState(): LessonDetailUiState {
        val lessonDateInfoUiState = if (lessonDates.contains(focusDate).not()) {
            LessonDateInfoUiState.NoLesson
        } else if (lessonAbsentDates.contains(focusDate)) {
            LessonDateInfoUiState.AbsentLesson
        } else if (lessonAttendanceDates.contains(focusDate)) {
            val lessonIndex = lessonAttendanceDates.indexOf(focusDate)
            LessonDateInfoUiState.CompletedLesson(lessonNumberOfProgress, lessonIndex)
        } else {
            LessonDateInfoUiState.ScheduleLesson
        }
        return LessonDetailUiState(
            focusDate = focusDate,
            lessonDateInfoUiState = lessonDateInfoUiState,
            lessonDates = lessonDates,
            lessonAttendanceDates = lessonAttendanceDates.toImmutableSet(),
            lessonAbsentDates = lessonAbsentDates
        )
    }
}

data class LessonDetailUiState(
    val focusDate: Long,
    val lessonDateInfoUiState: LessonDateInfoUiState,
    val lessonDates: ImmutableSet<Long>,
    val lessonAttendanceDates: ImmutableSet<Long>,
    val lessonAbsentDates: ImmutableSet<Long>,
) : UiState

sealed interface LessonDateInfoUiState {
    data object NoLesson : LessonDateInfoUiState

    data object ScheduleLesson : LessonDateInfoUiState

    data object AbsentLesson : LessonDateInfoUiState

    data class CompletedLesson(
        val lessonNumberOfProgress: Int,
        val lessonIndex: Int
    ) : LessonDateInfoUiState
}