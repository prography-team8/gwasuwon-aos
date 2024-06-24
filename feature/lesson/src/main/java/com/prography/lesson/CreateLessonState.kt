package com.prography.lesson

import com.prography.domain.lesson.model.LessonDay
import com.prography.domain.lesson.model.LessonDuration
import com.prography.domain.lesson.model.LessonSubject
import com.prography.usm.state.MachineInternalState
import com.prography.usm.state.UiState
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentSetOf

/**
 * Created by MyeongKi.
 */
data class CreateLessonMachineState(
    val screenType: CreateLessonScreenType = CreateLessonScreenType.DEFAULT_INFO,
    val studentName: String = "",
    val schoolYear: String = "",
    val memo: String = "",
    val lessonSubject: LessonSubject? = null,
    val lessonDuration: LessonDuration? = null,
    val lessonDay: ImmutableSet<LessonDay> = persistentSetOf(),
    val lessonNumberOfProgress: Int? = null,
    val lessonStartDate: String? = null,
    val lessonContractUrl: String = "",
    val isLoading: Boolean = false
) : MachineInternalState<CreateLessonUiState> {
    fun availableDefaultInfo() = studentName.isNotEmpty() && schoolYear.isNotEmpty()
    fun availableAdditionalInfo() = lessonSubject != null
            && lessonDuration != null
            && lessonDay.isNotEmpty()
            && lessonNumberOfProgress != null
            && lessonStartDate != null
    override fun toUiState(): CreateLessonUiState {
        return when (screenType) {
            CreateLessonScreenType.DEFAULT_INFO -> {
                CreateLessonUiState.DefaultInfo(
                    studentName = studentName,
                    schoolYear = schoolYear,
                    memo = memo,
                    availableNextBtn = availableDefaultInfo()
                )
            }

            CreateLessonScreenType.ADDITIONAL_INFO -> {
                CreateLessonUiState.AdditionalInfo(
                    isLoading = isLoading,
                    lessonSubject = lessonSubject,
                    lessonDuration = lessonDuration,
                    lessonDay = lessonDay,
                    lessonNumberOfProgress = lessonNumberOfProgress,
                    lessonStartDate = lessonStartDate,
                    availableNextBtn = availableAdditionalInfo()
                )
            }

            CreateLessonScreenType.COMPLETE -> {
                CreateLessonUiState.Complete
            }
        }
    }
}

sealed interface CreateLessonUiState : UiState {
    data class DefaultInfo(
        val studentName: String,
        val schoolYear: String,
        val memo: String,
        val availableNextBtn: Boolean
    ) : CreateLessonUiState

    data class AdditionalInfo(
        val lessonSubject: LessonSubject? = null,
        val lessonDuration: LessonDuration? = null,
        val lessonDay: ImmutableSet<LessonDay> = persistentSetOf(),
        val lessonNumberOfProgress: Int? = null,
        val lessonStartDate: String? = null,
        val availableNextBtn: Boolean,
        val isLoading: Boolean = false
    ) : CreateLessonUiState

    data object Complete : CreateLessonUiState
}

enum class CreateLessonScreenType(val page: Int) {
    DEFAULT_INFO(1),
    ADDITIONAL_INFO(2),
    COMPLETE(3)
    ;

    fun isFirstPage() = this.page == 1
    fun next(): CreateLessonScreenType? {
        return entries.find { (this.page + 1) == it.page }
    }
    fun prev(): CreateLessonScreenType? {
        return entries.find { (this.page - 1) == it.page }
    }
}