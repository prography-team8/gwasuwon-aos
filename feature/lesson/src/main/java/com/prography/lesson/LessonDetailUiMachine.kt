package com.prography.lesson

import NavigationEvent
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
    loadLessonUseCase: LoadLessonUseCase,
    loadLessonDatesUseCase: LoadLessonDatesUseCase
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
            //FIXME
            navigateFlow.emit(NavigationEvent.NavigateLessonContractQrRoute(lessonId = lessonId))
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
    override val outerNotifyScenarioActionFlow = merge(
        popBackFlow,
        navigateLessonCertificationQrFlow,
        navigateLessonInfoDetailFlow
    )

    init {
        initMachine()
    }

    override fun mergeStateChangeScenarioActionsFlow(): Flow<LessonDetailMachineState> {
        return merge(
            refreshFlow,
            focusDateFlow,
            checkByAttendanceFlow
        )
    }
}
