package com.prography.lesson

import com.prography.domain.lesson.model.Lesson
import com.prography.domain.lesson.model.LessonDay
import com.prography.domain.lesson.model.LessonDuration
import com.prography.domain.lesson.model.LessonSubject
import com.prography.usm.state.MachineInternalState
import com.prography.usm.state.UiState
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toImmutableSet

/**
 * Created by MyeongKi.
 */
data class LessonInfoDetailMachineState(
    val originalLessonInfo: Lesson? = null,
    val studentName: String = originalLessonInfo?.studentName ?: "",
    val schoolYear: String = originalLessonInfo?.grade ?: "",
    val lessonSubject: LessonSubject? = originalLessonInfo?.subject,
    val lessonDuration: LessonDuration? = originalLessonInfo?.sessionDuration,
    val lessonDay: ImmutableSet<LessonDay> = originalLessonInfo?.classDays?.toImmutableSet() ?: persistentSetOf(),
    val lessonNumberOfProgress: Int? = originalLessonInfo?.numberOfSessions,
    val lessonNumberOfPostpone: Int? = originalLessonInfo?.rescheduleCount,
    val lessonStartDateTime: Long? = originalLessonInfo?.startDate,
    val isLoading: Boolean = false
) : MachineInternalState<LessonInfoDetailUiState> {
    private fun isDiffFromOriginal(): Boolean {
        return studentName != originalLessonInfo?.studentName
                || schoolYear != originalLessonInfo.grade
                || lessonSubject != originalLessonInfo.subject
                || lessonDuration != originalLessonInfo.sessionDuration
                || lessonDay != originalLessonInfo.classDays.toImmutableSet()
                || lessonNumberOfProgress != originalLessonInfo.numberOfSessions
                || lessonNumberOfPostpone != originalLessonInfo.rescheduleCount
                || lessonStartDateTime != originalLessonInfo.startDate
    }

    private fun isValidLesson(): Boolean {
        return studentName.isNotEmpty()
                && schoolYear.isNotEmpty()
                && lessonSubject != null
                && lessonDuration != null
                && lessonDay.isNotEmpty()
                && lessonNumberOfProgress != null
                && lessonStartDateTime != null
    }

    override fun toUiState(): LessonInfoDetailUiState {
        return LessonInfoDetailUiState(
            isVisibleUpdateBtn = isDiffFromOriginal() && isValidLesson(),
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
    val lessonSubject: LessonSubject?,
    val lessonDuration: LessonDuration?,
    val lessonDay: ImmutableSet<LessonDay>,
    val lessonNumberOfProgress: Int?,
    val lessonNumberOfPostpone: Int?,
    val lessonStartDateTime: Long?,
    val isLoading: Boolean = false
) : UiState