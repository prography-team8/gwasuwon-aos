package com.prography.lesson

import NavigationEvent
import com.prography.domain.lesson.CommonLessonEvent
import com.prography.domain.lesson.request.CreateLessonRequestOption
import com.prography.domain.lesson.usecase.CreateLessonUseCase
import com.prography.usm.holder.UiStateMachine
import com.prography.usm.result.Result
import com.prography.usm.result.asResult
import kotlinx.collections.immutable.toImmutableSet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.transform

/**
 * Created by MyeongKi.
 */
class CreateLessonUiMachine(
    coroutineScope: CoroutineScope,
    navigateFlow: MutableSharedFlow<NavigationEvent>,
    commonLessonEvent: MutableSharedFlow<CommonLessonEvent>,
    createLessonUseCase: CreateLessonUseCase,
) : UiStateMachine<
        CreateLessonUiState,
        CreateLessonMachineState,
        CreateLessonActionEvent,
        CreateLessonIntent>(coroutineScope) {
    override var machineInternalState: CreateLessonMachineState = CreateLessonMachineState()

    private val popBackFlow = actionFlow
        .filterIsInstance<CreateLessonActionEvent.PopBack>()
        .map {
            if (machineInternalState.screenType.isFirstPage()) {
                navigateFlow.emit(NavigationEvent.PopBack)
                machineInternalState
            } else {
                machineInternalState.copy(screenType = machineInternalState.screenType.prev() ?: machineInternalState.screenType)
            }

        }

    private val createLessonFlow = actionFlow
        .filterIsInstance<CreateLessonActionEvent.CreateLesson>()
        .filter {
            machineInternalState.availableDefaultInfo()
                    && machineInternalState.availableAdditionalInfo()
        }
        .transform {
            emitAll(
                createLessonUseCase(
                    CreateLessonRequestOption(
                        studentName = machineInternalState.studentName,
                        grade = machineInternalState.schoolYear,
                        memo = machineInternalState.memo,
                        subject = machineInternalState.lessonSubject!!,
                        lessonDays = machineInternalState.lessonDay.toList(),
                        sessionDuration = machineInternalState.lessonDuration!!,
                        numberOfSessions = machineInternalState.lessonNumberOfProgress!!,
                        startDate = machineInternalState.lessonStartDateTime!!,
                        rescheduleCount = machineInternalState.lessonNumberOfPostpone!!
                    )
                ).asResult()
            )
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
                    commonLessonEvent.emit(CommonLessonEvent.NotifyUpdateLesson(it.data))
                    navigateFlow.emit(NavigationEvent.NavigateSuccessCreateLessonRoute(it.data.lessonId))
                    machineInternalState.copy(
                        isLoading = false,
                    )
                }
            }
        }

    private val goToNextPageFlow = actionFlow
        .filterIsInstance<CreateLessonActionEvent.GoToNextPage>()
        .filter { machineInternalState.screenType.isLastPage().not() }
        .map {
            machineInternalState.copy(
                screenType = machineInternalState.screenType.next() ?: machineInternalState.screenType
            )
        }

    private val updateStudentNameFlow = actionFlow
        .filterIsInstance<CreateLessonActionEvent.UpdateStudentName>()
        .map {
            machineInternalState.copy(
                studentName = it.studentName
            )
        }

    private val updateSchoolYearFlow = actionFlow
        .filterIsInstance<CreateLessonActionEvent.UpdateSchoolYear>()
        .map {
            machineInternalState.copy(
                schoolYear = it.schoolYear
            )
        }

    private val updateMemoFlow = actionFlow
        .filterIsInstance<CreateLessonActionEvent.UpdateMemo>()
        .map {
            machineInternalState.copy(
                memo = it.memo
            )
        }
    private val updateLessonSubjectFlow = actionFlow
        .filterIsInstance<CreateLessonActionEvent.UpdateLessonSubject>()
        .map {
            machineInternalState.copy(
                lessonSubject = it.lessonSubject
            )
        }
    private val updateLessonDurationFlow = actionFlow
        .filterIsInstance<CreateLessonActionEvent.UpdateLessonDuration>()
        .map {
            machineInternalState.copy(
                lessonDuration = it.lessonDuration
            )
        }

    private val toggleLessonDayFlow = actionFlow
        .filterIsInstance<CreateLessonActionEvent.ToggleLessonDay>()
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
        .filterIsInstance<CreateLessonActionEvent.UpdateLessonNumberOfProgress>()
        .map {
            machineInternalState.copy(
                lessonNumberOfProgress = it.lessonNumberOfProgress
            )
        }
    private val updateLessonNumberOfPostponeFlow = actionFlow
        .filterIsInstance<CreateLessonActionEvent.UpdateLessonNumberOfPostpone>()
        .map {
            machineInternalState.copy(
                lessonNumberOfPostpone = it.lessonNumberOfPostpone
            )
        }

    private val updateLessonStartDateFlow = actionFlow
        .filterIsInstance<CreateLessonActionEvent.UpdateLessonStartDate>()
        .map {
            machineInternalState.copy(
                lessonStartDateTime = it.lessonStartDateTime
            )
        }

    private val hideDialogFlow = actionFlow
        .filterIsInstance<CreateLessonActionEvent.HideDialog>()
        .map {
            machineInternalState.copy(
                dialog = CreateLessonDialog.None
            )
        }

    private val showPostponeInformationDialogFlow = actionFlow
        .filterIsInstance<CreateLessonActionEvent.ShowPostponeInformationDialog>()
        .map {
            machineInternalState.copy(
                dialog = CreateLessonDialog.PostponeInformation
            )
        }
    override val outerNotifyScenarioActionFlow = null

    init {
        initMachine()
    }

    override fun mergeStateChangeScenarioActionsFlow(): Flow<CreateLessonMachineState> {
        return merge(
            createLessonFlow,
            goToNextPageFlow,
            updateStudentNameFlow,
            updateSchoolYearFlow,
            updateMemoFlow,
            updateLessonSubjectFlow,
            updateLessonDurationFlow,
            toggleLessonDayFlow,
            updateLessonNumberOfProgressFlow,
            updateLessonStartDateFlow,
            updateLessonNumberOfPostponeFlow,
            popBackFlow,
            hideDialogFlow,
            showPostponeInformationDialogFlow
        )
    }
}
