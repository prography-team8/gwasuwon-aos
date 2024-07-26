package com.prography.lesson

import NavigationEvent
import com.prography.domain.lesson.CommonLessonEvent
import com.prography.domain.lesson.request.CreateLessonRequestOption
import com.prography.domain.lesson.request.UpdateLessonRequestOption
import com.prography.domain.lesson.usecase.LoadLessonInfoDetailUseCase
import com.prography.domain.lesson.usecase.UpdateLessonUseCase
import com.prography.ui.component.CommonDialogState
import com.prography.usm.holder.UiStateMachine
import com.prography.usm.result.Result
import com.prography.usm.result.asResult
import com.prography.utils.network.NetworkUnavailableException
import kotlinx.collections.immutable.toImmutableSet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.transform
import kotlin.math.min

/**
 * Created by MyeongKi.
 */
class ExtensionLessonUiMachine(
    lessonId: Long,
    coroutineScope: CoroutineScope,
    navigateFlow: MutableSharedFlow<NavigationEvent>,
    commonLessonEvent: MutableSharedFlow<CommonLessonEvent>,
    updateLessonUseCase: UpdateLessonUseCase,
    loadLessonInfoDetailUseCase: LoadLessonInfoDetailUseCase,
) : UiStateMachine<
        ExtensionLessonUiState,
        ExtensionLessonMachineState,
        ExtensionLessonActionEvent,
        ExtensionLessonIntent>(coroutineScope) {
    override var machineInternalState: ExtensionLessonMachineState = ExtensionLessonMachineState()

    private val refreshFlow = actionFlow
        .filterIsInstance<ExtensionLessonActionEvent.Refresh>()
        .transform {
            emitAll(loadLessonInfoDetailUseCase(lessonId).asResult())
        }
        .map {
            when (it) {
                is Result.Error -> {
                    machineInternalState.copy(isLoading = false)
                }

                is Result.Loading -> {
                    machineInternalState.copy(isLoading = true)
                }

                is Result.Success -> {
                    machineInternalState.copy(
                        isLoading = false,
                        studentName = it.data.studentName,
                        schoolYear = it.data.grade,
                        memo = it.data.memo,
                        lessonSubject = it.data.subject,
                        lessonDay = it.data.classDays.toImmutableSet(),
                        lessonDuration = it.data.sessionDuration,
                        lessonNumberOfProgress = it.data.numberOfSessions,
                        lessonStartDateTime = it.data.startDate,
                        lessonNumberOfPostpone = it.data.rescheduleCount
                    )
                }
            }
        }
    private val popBackFlow = actionFlow
        .filterIsInstance<ExtensionLessonActionEvent.PopBack>()
        .onEach {
            navigateFlow.emit(NavigationEvent.PopBack)
        }

    private val extensionLessonFlow = actionFlow
        .filterIsInstance<ExtensionLessonActionEvent.ExtensionLesson>()
        .filter {
            machineInternalState.availableExtensionLesson()
        }
        .transform {
            emitAll(
                updateLessonUseCase(
                    UpdateLessonRequestOption(
                        lessonId = lessonId,
                        updateOption = CreateLessonRequestOption(
                            studentName = machineInternalState.studentName,
                            grade = machineInternalState.schoolYear,
                            memo = machineInternalState.memo,
                            subject = machineInternalState.lessonSubject!!,
                            lessonDays = machineInternalState.lessonDay.toList(),
                            sessionDuration = machineInternalState.lessonDuration!!,
                            numberOfSessions = machineInternalState.lessonNumberOfProgress!!,
                            startDate = machineInternalState.lessonStartDateTime!!,
                            rescheduleCount = machineInternalState.lessonNumberOfPostpone!!
                        ),
                    )
                ).asResult()
            )
        }
        .map {
            when (it) {
                is Result.Error -> {
                    val dialog = if (it.exception is NetworkUnavailableException) {
                        CommonDialogState.NetworkError
                    } else {
                        CommonDialogState.UnknownError
                    }
                    machineInternalState.copy(
                        isLoading = false,
                        dialog = ExtensionLessonDialog.CreateLessonCommonDialog(dialog)
                    )
                }

                is Result.Loading -> {
                    machineInternalState.copy(isLoading = true)
                }

                is Result.Success -> {
                    commonLessonEvent.emit(CommonLessonEvent.NotifyUpdateLesson(it.data))
                    navigateFlow.emit(NavigationEvent.PopBack)
                    machineInternalState.copy(
                        isLoading = false,
                    )
                }
            }
        }

    private val updateLessonSubjectFlow = actionFlow
        .filterIsInstance<ExtensionLessonActionEvent.UpdateLessonSubject>()
        .map {
            machineInternalState.copy(
                lessonSubject = it.lessonSubject
            )
        }
    private val updateLessonDurationFlow = actionFlow
        .filterIsInstance<ExtensionLessonActionEvent.UpdateLessonDuration>()
        .map {
            machineInternalState.copy(
                lessonDuration = it.lessonDuration
            )
        }

    private val toggleLessonDayFlow = actionFlow
        .filterIsInstance<ExtensionLessonActionEvent.ToggleLessonDay>()
        .map { event ->
            val preContains = machineInternalState.lessonDay.contains(event.lessonDay)
            if (preContains.not()) {
                machineInternalState.copy(
                    lessonDay = machineInternalState.lessonDay.asSequence()
                        .plus(event.lessonDay)
                        .toImmutableSet()
                )
            } else {
                machineInternalState.copy(
                    lessonDay = machineInternalState.lessonDay.asSequence()
                        .filter { it != event.lessonDay }
                        .toImmutableSet()
                )
            }
        }
    private val updateLessonNumberOfProgressFlow = actionFlow
        .filterIsInstance<ExtensionLessonActionEvent.UpdateLessonNumberOfProgress>()
        .map {
            machineInternalState.copy(
                lessonNumberOfProgress = min(it.lessonNumberOfProgress, MAX_PROGRESS)
            )
        }
    private val updateLessonNumberOfPostponeFlow = actionFlow
        .filterIsInstance<ExtensionLessonActionEvent.UpdateLessonNumberOfPostpone>()
        .map {
            machineInternalState.copy(
                lessonNumberOfPostpone = it.lessonNumberOfPostpone
            )
        }

    private val updateLessonStartDateFlow = actionFlow
        .filterIsInstance<ExtensionLessonActionEvent.UpdateLessonStartDate>()
        .map {
            machineInternalState.copy(
                lessonNewStartDateTime = it.lessonStartDateTime
            )
        }

    private val hideDialogFlow = actionFlow
        .filterIsInstance<ExtensionLessonActionEvent.HideDialog>()
        .map {
            machineInternalState.copy(
                dialog = ExtensionLessonDialog.None
            )
        }

    private val showPostponeInformationDialogFlow = actionFlow
        .filterIsInstance<ExtensionLessonActionEvent.ShowPostponeInformationDialog>()
        .map {
            machineInternalState.copy(
                dialog = ExtensionLessonDialog.PostponeInformation
            )
        }
    override val outerNotifyScenarioActionFlow = merge(
        popBackFlow
    )

    init {
        initMachine()
        eventInvoker(ExtensionLessonActionEvent.Refresh)
    }

    override fun mergeStateChangeScenarioActionsFlow(): Flow<ExtensionLessonMachineState> {
        return merge(
            refreshFlow,
            extensionLessonFlow,
            updateLessonSubjectFlow,
            updateLessonDurationFlow,
            toggleLessonDayFlow,
            updateLessonNumberOfProgressFlow,
            updateLessonStartDateFlow,
            updateLessonNumberOfPostponeFlow,
            hideDialogFlow,
            showPostponeInformationDialogFlow
        )
    }

    companion object {
        const val MAX_PROGRESS = 100
    }
}
