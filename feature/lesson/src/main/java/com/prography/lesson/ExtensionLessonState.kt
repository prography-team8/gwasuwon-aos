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
data class ExtensionLessonMachineState(
    val studentName: String = "",
    val schoolYear: String = "",
    val memo: String = "",
    val lessonSubject: LessonSubject? = null,
    val lessonDuration: LessonDuration? = null,
    val lessonDay: ImmutableSet<LessonDay> = persistentSetOf(),
    val lessonNumberOfProgress: Int? = null,
    val lessonNumberOfPostpone: Int? = null,
    val lessonStartDateTime: Long? = null,
    val lessonNewStartDateTime: Long? = null,
    val isLoading: Boolean = false,
    val dialog: ExtensionLessonDialog = ExtensionLessonDialog.None
) : MachineInternalState<ExtensionLessonUiState> {
    fun availableExtensionLesson() = lessonSubject != null
            && lessonDuration != null
            && lessonDay.isNotEmpty()
            && lessonNumberOfProgress != null
            && lessonStartDateTime != null
            && lessonNumberOfPostpone != null
            && lessonNewStartDateTime != null
            && (lessonNewStartDateTime != lessonStartDateTime)

    override fun toUiState(): ExtensionLessonUiState {
        return ExtensionLessonUiState(
            additionalInfo = AdditionalInfo(
                lessonSubject = lessonSubject,
                lessonDuration = lessonDuration,
                lessonDay = lessonDay,
                lessonNumberOfProgress = lessonNumberOfProgress,
                lessonNumberOfPostpone = lessonNumberOfPostpone,
                lessonStartDateTime = lessonNewStartDateTime ?: lessonStartDateTime,
            ),
            dialog = dialog,
            isLoading = isLoading,
            availableExtensionBtn = availableExtensionLesson()
        )
    }
}

data class ExtensionLessonUiState(
    val additionalInfo: AdditionalInfo,
    val availableExtensionBtn: Boolean,
    val isLoading: Boolean,
    val dialog: ExtensionLessonDialog
) : UiState

sealed interface ExtensionLessonDialog {
    data object None: ExtensionLessonDialog
    data class CreateLessonCommonDialog(
        val state: CommonDialogState
    ) : ExtensionLessonDialog
    data object PostponeInformation : ExtensionLessonDialog
}