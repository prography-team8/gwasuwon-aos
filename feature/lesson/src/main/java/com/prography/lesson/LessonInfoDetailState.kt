package com.prography.lesson

import com.prography.domain.lesson.model.Lesson
import com.prography.domain.lesson.model.LessonDay
import com.prography.domain.lesson.model.LessonDuration
import com.prography.domain.lesson.model.LessonSubject
import com.prography.usm.state.MachineInternalState
import com.prography.usm.state.UiState
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.toImmutableSet

/**
 * Created by MyeongKi.
 */
data class LessonInfoDetailMachineState(
    val originalLessonInfo: Lesson,
    val studentName: String = originalLessonInfo.studentName,
    val schoolYear: String = originalLessonInfo.schoolYear,
    val lessonSubject: LessonSubject = originalLessonInfo.lessonSubject,
    val lessonDuration: LessonDuration = originalLessonInfo.lessonDuration,
    val lessonDay: ImmutableSet<LessonDay> = originalLessonInfo.lessonDay.toImmutableSet(),
    val lessonNumberOfProgress: Int = originalLessonInfo.lessonNumberOfProgress,
    val lessonNumberOfPostpone: Int = originalLessonInfo.lessonNumberOfPostpone,
    val lessonStartDateTime: Long = originalLessonInfo.lessonStartDateTime,
    val isLoading: Boolean = false
) : MachineInternalState<LessonInfoDetailUiState> {
    private fun isDiffFromOriginal(): Boolean {
        return studentName != originalLessonInfo.studentName
                || schoolYear != originalLessonInfo.schoolYear
                || lessonSubject != originalLessonInfo.lessonSubject
                || lessonDuration != originalLessonInfo.lessonDuration
                || lessonDay != originalLessonInfo.lessonDay.toImmutableSet()
                || lessonNumberOfProgress != originalLessonInfo.lessonNumberOfProgress
                || lessonNumberOfPostpone != originalLessonInfo.lessonNumberOfPostpone
                || lessonStartDateTime != originalLessonInfo.lessonStartDateTime
    }

    override fun toUiState(): LessonInfoDetailUiState {
        return LessonInfoDetailUiState(
            isVisibleUpdateBtn = isDiffFromOriginal(),
            studentName = studentName,
            schoolYear = schoolYear,
            lessonSubject = lessonSubject,
            lessonDuration = lessonDuration,
            lessonDay = lessonDay,
            lessonNumberOfProgress = lessonNumberOfProgress,
            lessonNumberOfPostpone = lessonNumberOfPostpone,
            lessonStartDateTime = lessonStartDateTime,
        )
    }
}

data class LessonInfoDetailUiState(
    val isVisibleUpdateBtn: Boolean = false,
    val studentName: String,
    val schoolYear: String,
    val lessonSubject: LessonSubject,
    val lessonDuration: LessonDuration,
    val lessonDay: ImmutableSet<LessonDay>,
    val lessonNumberOfProgress: Int,
    val lessonNumberOfPostpone: Int,
    val lessonStartDateTime: Long,
    val isLoading: Boolean = false
) : UiState