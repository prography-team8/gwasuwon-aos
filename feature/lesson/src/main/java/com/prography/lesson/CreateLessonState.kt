package com.prography.lesson

import com.prography.domain.lesson.model.LessonDay
import com.prography.domain.lesson.model.LessonDuration
import com.prography.domain.lesson.model.LessonSubject
import com.prography.ui.component.CommonDialogState
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
    val lessonNumberOfPostpone: Int? = null,
    val lessonStartDateTime: Long? = null,
    val isLoading: Boolean = false,
    val dialog: CreateLessonDialog = CreateLessonDialog.None
) : MachineInternalState<CreateLessonUiState> {
    fun availableDefaultInfo() = studentName.isNotEmpty() && schoolYear.isNotEmpty() && memo.isNotEmpty()
    fun availableAdditionalInfo() = lessonSubject != null
            && lessonDuration != null
            && lessonDay.isNotEmpty()
            && lessonNumberOfProgress != null
            && lessonStartDateTime != null
            && lessonNumberOfPostpone != null

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
                CreateLessonUiState.CreateAdditionalInfo(
                    additionalInfo = AdditionalInfo(
                        lessonSubject = lessonSubject,
                        lessonDuration = lessonDuration,
                        lessonDay = lessonDay,
                        lessonNumberOfProgress = lessonNumberOfProgress,
                        lessonNumberOfPostpone = lessonNumberOfPostpone,
                        lessonStartDateTime = lessonStartDateTime,
                    ),
                    dialog = dialog,
                    isLoading = isLoading,
                    availableNextBtn = availableAdditionalInfo(),
                )
            }
        }
    }
}

data class AdditionalInfo(
    val lessonSubject: LessonSubject? = null,
    val lessonDuration: LessonDuration? = null,
    val lessonDay: ImmutableSet<LessonDay> = persistentSetOf(),
    val lessonNumberOfProgress: Int? = null,
    val lessonNumberOfPostpone: Int? = null,
    val lessonStartDateTime: Long? = null
)

sealed interface CreateLessonUiState : UiState {
    data class DefaultInfo(
        val studentName: String,
        val schoolYear: String,
        val memo: String,
        val availableNextBtn: Boolean
    ) : CreateLessonUiState

    data class CreateAdditionalInfo(
        val additionalInfo: AdditionalInfo,
        val availableNextBtn: Boolean,
        val isLoading: Boolean,
        val dialog: CreateLessonDialog
    ) : CreateLessonUiState
}

enum class CreateLessonScreenType(val page: Int) {
    DEFAULT_INFO(1),
    ADDITIONAL_INFO(2),
    ;

    fun isFirstPage() = this.page == 1
    fun isLastPage() = this.page == entries.last().page
    fun next(): CreateLessonScreenType? {
        return entries.find { (this.page + 1) == it.page }
    }

    fun prev(): CreateLessonScreenType? {
        return entries.find { (this.page - 1) == it.page }
    }
}

sealed interface CreateLessonDialog {
    data object None : CreateLessonDialog
    data class CreateLessonCommonDialog(
        val state: CommonDialogState
    ) : CreateLessonDialog
    data object PostponeInformation : CreateLessonDialog
}