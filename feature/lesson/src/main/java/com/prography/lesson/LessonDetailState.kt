package com.prography.lesson

import com.prography.usm.state.MachineInternalState
import com.prography.usm.state.UiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toImmutableSet

/**
 * Created by MyeongKi.
 */
data class LessonDetailMachineState(
    val studentName: String = "",
    val lessonNumberOfProgress: Int = 0,
    val focusDate: Long = -1,
    val lessonDates: ImmutableSet<Long> = persistentSetOf(),
    val lessonAttendanceDates: ImmutableList<Long> = persistentListOf(),
    val lessonAbsentDates: ImmutableSet<Long> = persistentSetOf(),
    val isLoading: Boolean = false,
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
            lessonAbsentDates = lessonAbsentDates,
            isLoading = isLoading
        )
    }
}

data class LessonDetailUiState(
    val focusDate: Long,
    val lessonDateInfoUiState: LessonDateInfoUiState,
    val lessonDates: ImmutableSet<Long>,
    val lessonAttendanceDates: ImmutableSet<Long>,
    val lessonAbsentDates: ImmutableSet<Long>,
    val isLoading: Boolean,
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