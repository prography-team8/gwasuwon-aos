package com.prography.lesson

import NavigationEvent
import com.prography.domain.lesson.CommonLessonEvent
import com.prography.domain.lesson.usecase.DeleteLessonUseCase
import com.prography.domain.lesson.usecase.LoadLessonDatesUseCase
import com.prography.domain.lesson.usecase.LoadLessonUseCase
import com.prography.usm.holder.UiStateMachine
import com.prography.usm.result.Result
import com.prography.usm.result.asResult
import com.prography.utils.date.DateUtils
import com.prography.utils.date.toKrMonthDateTime
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentSet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.transform

/**
 * Created by MyeongKi.
 */
class LessonDetailUiMachine(
    lessonId: Long,
    coroutineScope: CoroutineScope,
    navigateFlow: MutableSharedFlow<NavigationEvent>,
    commonLessonEvent: MutableSharedFlow<CommonLessonEvent>,
    loadLessonUseCase: LoadLessonUseCase,
    loadLessonDatesUseCase: LoadLessonDatesUseCase,
    deleteLessonUseCase: DeleteLessonUseCase,
) : UiStateMachine<
        LessonDetailUiState,
        LessonDetailMachineState,
        LessonDetailActionEvent,
        LessonDetailIntent
        >(coroutineScope) {
    override var machineInternalState: LessonDetailMachineState = LessonDetailMachineState()

    private val popBackFlow = actionFlow
        .filterIsInstance<LessonDetailActionEvent.PopBack>()
        .onEach {
            navigateFlow.emit(NavigationEvent.PopBack)
        }
    private val navigateLessonCertificationQrFlow = actionFlow
        .filterIsInstance<LessonDetailActionEvent.NavigateLessonCertificationQr>()
        .onEach {
            navigateFlow.emit(NavigationEvent.NavigateLessonCertificationQrRoute(lessonId = lessonId))
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    private val refreshFlow = actionFlow
        .filterIsInstance<LessonDetailActionEvent.Refresh>()
        .transform {
            emitAll(loadLessonUseCase(lessonId).flatMapConcat { lesson ->
                loadLessonDatesUseCase(lesson).map {
                    Pair(lesson, it)
                }
            }.asResult())
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
                    val lesson = it.data.first
                    val dates = it.data.second
                    LessonDetailMachineState(
                        isLoading = false,
                        lessonDates = dates.toPersistentSet(),
                        studentName = lesson.studentName,
                        lessonNumberOfProgress = lesson.lessonNumberOfProgress,
                        focusDate = DateUtils.getCurrentDateTime().toKrMonthDateTime(),
                        lessonAttendanceDates = lesson.lessonAttendanceDates.toKrMonthDateTime().toImmutableList(),
                        lessonAbsentDates = lesson.lessonAbsentDates.toKrMonthDateTime().toPersistentSet()
                    )
                }
            }
        }
    private val focusDateFlow = actionFlow
        .filterIsInstance<LessonDetailActionEvent.FocusDate>()
        .map {
            machineInternalState.copy(
                focusDate = it.date
            )
        }
    private val checkByAttendanceFlow = actionFlow
        .filterIsInstance<LessonDetailActionEvent.CheckByAttendance>()
        .map {
            //FIXME usecase 추가 필요 단일 상태만 변경 필요.
            machineInternalState
        }

    private val navigateLessonInfoDetailFlow = actionFlow
        .filterIsInstance<LessonDetailActionEvent.NavigateLessonInfoDetail>()
        .onEach {
            navigateFlow.emit(NavigationEvent.NavigateLessonInfoDetailRoute(lessonId = lessonId))
        }

    private val deleteLessonFlow = actionFlow
        .filterIsInstance<LessonDetailActionEvent.DeleteLesson>()
        .transform {
            emitAll(deleteLessonUseCase(lessonId).asResult())
        }
        .onEach {
            when (it) {
                is Result.Success -> {
                    commonLessonEvent.emit(CommonLessonEvent.NotifyDeleteLesson(lessonId))
                    navigateFlow.emit(NavigationEvent.PopBack)
                }

                else -> Unit
            }
        }

    private val hideDialogFlow = actionFlow
        .filterIsInstance<LessonDetailActionEvent.HideDialog>()
        .map {
            machineInternalState.copy(
                dialog = LessonDetailDialog.None
            )
        }
    private val showDeleteLessonDialogFlow = actionFlow
        .filterIsInstance<LessonDetailActionEvent.ShowDeleteLessonDialog>()
        .map {
            machineInternalState.copy(
                dialog = LessonDetailDialog.DeleteLesson
            )
        }
    override val outerNotifyScenarioActionFlow = merge(
        popBackFlow,
        navigateLessonCertificationQrFlow,
        navigateLessonInfoDetailFlow,
        deleteLessonFlow
    )

    init {
        initMachine()
    }

    override fun mergeStateChangeScenarioActionsFlow(): Flow<LessonDetailMachineState> {
        return merge(
            refreshFlow,
            focusDateFlow,
            checkByAttendanceFlow,
            hideDialogFlow,
            showDeleteLessonDialogFlow
        )
    }
}
