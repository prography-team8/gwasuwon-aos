package com.prography.lesson

import NavigationEvent
import com.prography.domain.lesson.CommonLessonEvent
import com.prography.domain.lesson.request.UpdateLessonRequestOption
import com.prography.domain.lesson.usecase.LoadLessonInfoDetailUseCase
import com.prography.domain.lesson.usecase.UpdateLessonUseCase
import com.prography.usm.holder.UiStateMachine
import com.prography.usm.result.Result
import com.prography.usm.result.asResult
import kotlinx.collections.immutable.toImmutableSet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.transform

/**
 * Created by MyeongKi.
 */
class LessonInfoDetailUiMachine(
    lessonId: Long,
    coroutineScope: CoroutineScope,
    navigateFlow: MutableSharedFlow<NavigationEvent>,
    commonLessonEvent: MutableSharedFlow<CommonLessonEvent>,
    loadLessonInfoDetailUseCase: LoadLessonInfoDetailUseCase,
    updateLessonUseCase: UpdateLessonUseCase,
) : UiStateMachine<
        LessonInfoDetailUiState,
        LessonInfoDetailMachineState,
        LessonInfoDetailActionEvent,
        LessonInfoDetailIntent>(coroutineScope) {
    override var machineInternalState: LessonInfoDetailMachineState = LessonInfoDetailMachineState()

    private val refreshFlow = actionFlow
        .filterIsInstance<LessonInfoDetailActionEvent.Refresh>()
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
                    LessonInfoDetailMachineState(
                        isLoading = false,
                        originalLessonInfo = it.data
                    )
                }
            }
        }

    private val popBackFlow = actionFlow
        .filterIsInstance<LessonInfoDetailActionEvent.PopBack>()
        .onEach {
            navigateFlow.emit(NavigationEvent.PopBack)
        }

    private val updateLessonFlow = actionFlow
        .filterIsInstance<LessonInfoDetailActionEvent.UpdateLesson>()
        .transform {
            emitAll(
                updateLessonUseCase(
                    UpdateLessonRequestOption(
                        studentName = machineInternalState.studentName,
                        schoolYear = machineInternalState.schoolYear,
                        memo = machineInternalState.originalLessonInfo?.memo ?: "",
                        lessonSubject = machineInternalState.lessonSubject!!,
                        lessonDay = machineInternalState.lessonDay.toList(),
                        lessonDuration = machineInternalState.lessonDuration!!,
                        lessonNumberOfProgress = machineInternalState.lessonNumberOfProgress!!,
                        lessonStartDateTime = machineInternalState.lessonStartDateTime!!,
                        lessonNumberOfPostpone = machineInternalState.lessonNumberOfPostpone!!,
                        lessonId = machineInternalState.originalLessonInfo?.lessonId!!
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
                    LessonInfoDetailMachineState(
                        isLoading = false,
                        originalLessonInfo = it.data
                    )
                }
            }
        }

    private val updateStudentNameFlow = actionFlow
        .filterIsInstance<LessonInfoDetailActionEvent.UpdateStudentName>()
        .map {
            machineInternalState.copy(
                studentName = it.studentName
            )
        }

    private val updateSchoolYearFlow = actionFlow
        .filterIsInstance<LessonInfoDetailActionEvent.UpdateSchoolYear>()
        .map {
            machineInternalState.copy(
                schoolYear = it.schoolYear
            )
        }
    private val updateLessonSubjectFlow = actionFlow
        .filterIsInstance<LessonInfoDetailActionEvent.UpdateLessonSubject>()
        .map {
            machineInternalState.copy(
                lessonSubject = it.lessonSubject
            )
        }
    private val updateLessonDurationFlow = actionFlow
        .filterIsInstance<LessonInfoDetailActionEvent.UpdateLessonDuration>()
        .map {
            machineInternalState.copy(
                lessonDuration = it.lessonDuration
            )
        }

    private val toggleLessonDayFlow = actionFlow
        .filterIsInstance<LessonInfoDetailActionEvent.ToggleLessonDay>()
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
        .filterIsInstance<LessonInfoDetailActionEvent.UpdateLessonNumberOfProgress>()
        .map {
            machineInternalState.copy(
                lessonNumberOfProgress = it.lessonNumberOfProgress
            )
        }
    private val updateLessonNumberOfPostponeFlow = actionFlow
        .filterIsInstance<LessonInfoDetailActionEvent.UpdateLessonNumberOfPostpone>()
        .map {
            machineInternalState.copy(
                lessonNumberOfPostpone = it.lessonNumberOfPostpone
            )
        }

    private val updateLessonStartDateFlow = actionFlow
        .filterIsInstance<LessonInfoDetailActionEvent.UpdateLessonStartDate>()
        .map {
            machineInternalState.copy(
                lessonStartDateTime = it.lessonStartDateTime
            )
        }
    override val outerNotifyScenarioActionFlow = merge(
        popBackFlow
    )

    init {
        initMachine()
        eventInvoker(LessonInfoDetailActionEvent.Refresh)
    }

    override fun mergeStateChangeScenarioActionsFlow(): Flow<LessonInfoDetailMachineState> {
        return merge(
            refreshFlow,
            updateLessonFlow,
            updateStudentNameFlow,
            updateSchoolYearFlow,
            updateLessonSubjectFlow,
            updateLessonDurationFlow,
            toggleLessonDayFlow,
            updateLessonNumberOfProgressFlow,
            updateLessonNumberOfPostponeFlow,
            updateLessonStartDateFlow
        )
    }
}
