package com.prography.lesson

import NavigationEvent
import com.prography.domain.dialog.model.LessonDeductedDialogMeta
import com.prography.domain.dialog.usecase.IsShowingNotifyLessonDeductedDialogUseCase
import com.prography.domain.dialog.usecase.UpdateShownNotifyLessonDeductedDialogUseCase
import com.prography.domain.lesson.CommonLessonEvent
import com.prography.domain.lesson.model.LessonScheduleStatus
import com.prography.domain.lesson.usecase.DeleteLessonUseCase
import com.prography.domain.lesson.usecase.LoadLessonSchedulesUseCase
import com.prography.domain.lesson.usecase.UpdateAttendanceLessonUseCase
import com.prography.domain.lesson.usecase.UpdateForceAttendanceLessonUseCase
import com.prography.domain.qr.CommonQrEvent
import com.prography.domain.qr.model.AttendanceClassData
import com.prography.domain.qr.model.GwasuwonQr
import com.prography.domain.qr.model.GwasuwonQrType
import com.prography.ui.component.CommonDialogState
import com.prography.usm.holder.UiStateMachine
import com.prography.usm.result.Result
import com.prography.usm.result.asResult
import com.prography.utils.date.DateUtils
import com.prography.utils.network.NetworkUnavailableException
import kotlinx.collections.immutable.toPersistentSet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.transform
import kotlinx.serialization.json.Json
import kotlin.math.max

/**
 * Created by MyeongKi.
 */
class LessonDetailUiMachine(
    lessonId: Long,
    isTeacher: Boolean,
    coroutineScope: CoroutineScope,
    navigateFlow: MutableSharedFlow<NavigationEvent>,
    commonQrFlow: MutableSharedFlow<CommonQrEvent>,
    commonLessonEvent: MutableSharedFlow<CommonLessonEvent>,
    loadLessonSchedulesUseCase: LoadLessonSchedulesUseCase,
    deleteLessonUseCase: DeleteLessonUseCase,
    updateForceAttendanceLessonUseCase: UpdateForceAttendanceLessonUseCase,
    isShowingNotifyLessonDeductedDialogUseCase: IsShowingNotifyLessonDeductedDialogUseCase,
    updateShownNotifyLessonDeductedDialogUseCase: UpdateShownNotifyLessonDeductedDialogUseCase,
    updateAttendanceLessonUseCase: UpdateAttendanceLessonUseCase
) : UiStateMachine<
        LessonDetailUiState,
        LessonDetailMachineState,
        LessonDetailActionEvent,
        LessonDetailIntent
        >(coroutineScope, commonQrFlow.toLessonDetailAction()) {
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
            emitAll(loadLessonSchedulesUseCase(lessonId).flatMapConcat { lessonSchedule ->
                val lessonAbsentDatesSize = lessonSchedule.schedules.filter { it.status == LessonScheduleStatus.CANCELED }.size
                isShowingNotifyLessonDeductedDialogUseCase(lessonId, max(lessonAbsentDatesSize - lessonSchedule.rescheduleCount, 0))
                    .onEach { isShowingNotifyLessonDeductedDialog ->
                        if (isShowingNotifyLessonDeductedDialog) {
                            eventInvoker(LessonDetailActionEvent.ShowNotifyLessonDeductedDialog)
                        }
                    }.map {
                        lessonSchedule
                    }
            }.map { result ->
                Pair(result, it.nextEvent)
            }
                .asResult())
        }
        .map { result ->
            when (result) {
                is Result.Error -> {
                    val dialog = if (result.exception is NetworkUnavailableException) {
                        CommonDialogState.NetworkError
                    } else {
                        CommonDialogState.UnknownError
                    }
                    machineInternalState.copy(
                        isLoading = false,
                        dialog = LessonDetailDialog.LessonDetailCommonDialog(dialog)
                    )
                }

                is Result.Loading -> {
                    machineInternalState.copy(isLoading = true)
                }

                is Result.Success -> {
                    LessonDetailMachineState(
                        isLoading = false,
                        hasStudent = result.data.first.hasStudent,
                        lessonDates = result.data.first.schedules
                            .filter { it.status == LessonScheduleStatus.SCHEDULED }
                            .map { it.date }
                            .toPersistentSet(),
                        studentName = result.data.first.studentName,
                        lessonNumberOfPostpone = result.data.first.rescheduleCount,
                        focusDate = DateUtils.getCurrentDateTime(),
                        lessonAttendanceDates = result.data.first.schedules
                            .filter { it.status == LessonScheduleStatus.COMPLETED }
                            .map { it.date },
                        lessonAbsentDates = result.data.first.schedules
                            .filter { it.status == LessonScheduleStatus.CANCELED }
                            .map { LessonAbsentDate(date = it.date, it.id) }
                    ).also {
                        result.data.second?.let {
                            eventInvoker(it)
                        }
                    }
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
    private val updateForceAttendanceLessonFlow = actionFlow
        .filterIsInstance<LessonDetailActionEvent.UpdateForceAttendanceLesson>()
        .transform {
            machineInternalState.lessonAbsentDates
                .find { it.date == machineInternalState.focusDate }
                ?.run {
                    emitAll(
                        updateForceAttendanceLessonUseCase(
                            scheduleId = scheduleId
                        ).asResult()
                    )
                }
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
                        dialog = LessonDetailDialog.LessonDetailCommonDialog(dialog)
                    )
                }

                is Result.Loading -> {
                    machineInternalState.copy(isLoading = true)
                }

                is Result.Success -> {
                    machineInternalState.copy(isLoading = false)
                }
            }
        }
        .onEach {
            eventInvoker(LessonDetailActionEvent.Refresh(LessonDetailActionEvent.UpdateLessonDeducted))
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
        .map {
            when (it) {
                is Result.Success -> {
                    commonLessonEvent.emit(CommonLessonEvent.NotifyDeleteLesson(lessonId))
                    navigateFlow.emit(NavigationEvent.PopBack)
                    machineInternalState
                }

                is Result.Error -> {
                    val dialog = if (it.exception is NetworkUnavailableException) {
                        CommonDialogState.NetworkError
                    } else {
                        CommonDialogState.UnknownError
                    }
                    machineInternalState.copy(
                        isLoading = false,
                        dialog = LessonDetailDialog.LessonDetailCommonDialog(dialog)
                    )
                }

                is Result.Loading -> {
                    machineInternalState.copy(
                        isLoading = true
                    )
                }
            }
        }

    private val hideDialogFlow = actionFlow
        .filterIsInstance<LessonDetailActionEvent.HideDialog>()
        .map {
            machineInternalState.copy(
                dialog = LessonDetailDialog.None
            )
        }
    private val hideNotifyLessonDeductedDialog = actionFlow
        .filterIsInstance<LessonDetailActionEvent.HideNotifyLessonDeductedDialog>()
        .onEach {
            eventInvoker(LessonDetailActionEvent.UpdateLessonDeducted)
        }
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
    private val showNotifyLessonDeductedDialogFlow = actionFlow
        .filterIsInstance<LessonDetailActionEvent.ShowNotifyLessonDeductedDialog>()
        .map { isTeacher }
        .map {
            machineInternalState.copy(
                dialog = LessonDetailDialog.NotifyLessonDeducted
            )
        }

    private val updateLessonDeducted = actionFlow
        .filterIsInstance<LessonDetailActionEvent.UpdateLessonDeducted>()
        .transform {
            emitAll(
                updateShownNotifyLessonDeductedDialogUseCase(
                    LessonDeductedDialogMeta(
                        lessonId = lessonId,
                        lessonDeducted = max(machineInternalState.lessonAbsentDates.size - machineInternalState.lessonNumberOfPostpone, 0)
                    )
                ).asResult()
            )
        }
    private val recognizeQrFlow = actionFlow
        .filterIsInstance<LessonDetailActionEvent.RecognizeQr>()
        .onEach {
            commonQrFlow.emit(CommonQrEvent.RequestQrScan)
        }

    private val updateAttendanceLessonFlow = actionFlow
        .filterIsInstance<LessonDetailActionEvent.UpdateAttendanceLesson>()
        .transform {
            if (it.qrLessonId == lessonId) {
                emitAll(updateAttendanceLessonUseCase(it.qrLessonId).asResult())
            } else {
                emit(Result.Error(IllegalArgumentException("Invalid lessonId")))
            }
        }
        .onEach {
            if (it is Result.Success) {
                eventInvoker(LessonDetailActionEvent.Refresh())
            }
        }
        .map {
            when (it) {
                is Result.Success -> {
                    machineInternalState.copy(
                        isLoading = false
                    )
                }

                is Result.Error -> {
                    machineInternalState.copy(
                        isLoading = false,
                        dialog = LessonDetailDialog.CertificateLessonErrorDialog
                    )
                }

                is Result.Loading -> {
                    machineInternalState.copy(
                        isLoading = true
                    )
                }
            }
        }

    private val navigateInviteStudentQrFlow = actionFlow
        .filterIsInstance<LessonDetailActionEvent.NavigateInviteStudentQr>()
        .onEach {
            navigateFlow.emit(NavigationEvent.NavigateInviteStudentQrRoute(lessonId = lessonId))
        }

    override val outerNotifyScenarioActionFlow = merge(
        popBackFlow,
        navigateLessonCertificationQrFlow,
        navigateLessonInfoDetailFlow,
        updateLessonDeducted,
        recognizeQrFlow,
        navigateInviteStudentQrFlow
    )

    init {
        initMachine()
    }

    override fun mergeStateChangeScenarioActionsFlow(): Flow<LessonDetailMachineState> {
        return merge(
            refreshFlow,
            focusDateFlow,
            updateForceAttendanceLessonFlow,
            hideDialogFlow,
            showDeleteLessonDialogFlow,
            hideNotifyLessonDeductedDialog,
            showNotifyLessonDeductedDialogFlow,
            updateAttendanceLessonFlow,
            deleteLessonFlow
        )
    }
}

private fun MutableSharedFlow<CommonQrEvent>.toLessonDetailAction(): Flow<LessonDetailActionEvent> = this.mapNotNull {
    when (it) {
        is CommonQrEvent.GetOnSuccessQr -> {
            val jsonString: GwasuwonQr? = try {
                Json.decodeFromString(GwasuwonQr.serializer(), it.raw)
            } catch (e: Exception) {
                null
            }
            jsonString?.let { gwasuwonQr ->
                if (gwasuwonQr.data.type == GwasuwonQrType.ATTENDANCE_CLASS) {
                    (gwasuwonQr.data as? AttendanceClassData)?.classId?.let { lessonId ->
                        LessonDetailActionEvent.UpdateAttendanceLesson(lessonId)

                    }
                } else {
                    null
                }
            }
        }

        else -> null
    }
}