package com.prography.lesson

import NavigationEvent
import com.prography.domain.dialog.model.LessonDeductedDialogMeta
import com.prography.domain.dialog.usecase.IsShowingNotifyLessonDeductedDialogUseCase
import com.prography.domain.dialog.usecase.UpdateShownNotifyLessonDeductedDialogUseCase
import com.prography.domain.lesson.CommonLessonEvent
import com.prography.domain.lesson.request.CheckLessonByAttendanceRequestOption
import com.prography.domain.lesson.usecase.CertificateLessonUseCase
import com.prography.domain.lesson.usecase.CheckLessonByAttendanceUseCase
import com.prography.domain.lesson.usecase.DeleteLessonUseCase
import com.prography.domain.lesson.usecase.LoadLessonDatesUseCase
import com.prography.domain.lesson.usecase.LoadLessonUseCase
import com.prography.domain.qr.CommonQrEvent
import com.prography.domain.qr.model.GwasuwonQr
import com.prography.domain.qr.model.GwasuwonQrType
import com.prography.domain.qr.model.LessonCertificationData
import com.prography.usm.holder.UiStateMachine
import com.prography.usm.result.Result
import com.prography.usm.result.asResult
import com.prography.utils.date.DateUtils
import com.prography.utils.date.toKrMonthDateTime
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
    coroutineScope: CoroutineScope,
    navigateFlow: MutableSharedFlow<NavigationEvent>,
    commonQrFlow: MutableSharedFlow<CommonQrEvent>,
    commonLessonEvent: MutableSharedFlow<CommonLessonEvent>,
    loadLessonUseCase: LoadLessonUseCase,
    loadLessonDatesUseCase: LoadLessonDatesUseCase,
    deleteLessonUseCase: DeleteLessonUseCase,
    checkLessonByAttendanceUseCase: CheckLessonByAttendanceUseCase,
    isShowingNotifyLessonDeductedDialogUseCase: IsShowingNotifyLessonDeductedDialogUseCase,
    updateShownNotifyLessonDeductedDialogUseCase: UpdateShownNotifyLessonDeductedDialogUseCase,
    certificateLessonUseCase: CertificateLessonUseCase
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
            emitAll(loadLessonUseCase(lessonId).flatMapConcat { lesson ->
                loadLessonDatesUseCase(lesson).map {
                    Pair(lesson, it)
                }.flatMapConcat { lessonAndDates ->
                    isShowingNotifyLessonDeductedDialogUseCase(lessonId, max(lesson.lessonAbsentDates.size - lesson.lessonNumberOfPostpone, 0))
                        .onEach { isShowingNotifyLessonDeductedDialog ->
                            if (isShowingNotifyLessonDeductedDialog) {
                                eventInvoker(LessonDetailActionEvent.ShowNotifyLessonDeductedDialog)
                            }
                        }.map {
                            lessonAndDates
                        }
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
                        lessonNumberOfPostpone = lesson.lessonNumberOfPostpone,
                        focusDate = DateUtils.getCurrentDateTime(),
                        lessonAttendanceDates = lesson.lessonAttendanceDates,
                        lessonAbsentDates = lesson.lessonAbsentDates
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
        .transform {
            val lessonAbsentDatesKr = machineInternalState.lessonAbsentDates.asSequence().map { it.toKrMonthDateTime() }.toSet()
            lessonAbsentDatesKr
                .indexOf(machineInternalState.focusDate)
                .takeIf { it != -1 }
                ?.run {
                    emitAll(
                        checkLessonByAttendanceUseCase(
                            CheckLessonByAttendanceRequestOption(
                                lessonId = lessonId,
                                lessonAbsentDate = machineInternalState.lessonAbsentDates[this]
                            )
                        ).asResult()
                    )
                }
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
                        lessonAbsentDates = it.data.lessonAbsentDates,
                        lessonAttendanceDates = it.data.lessonAttendanceDates
                    )
                }
            }
        }
        .onEach {
            eventInvoker(LessonDetailActionEvent.UpdateLessonDeducted)
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

    private val certificateLessonFlow = actionFlow
        .filterIsInstance<LessonDetailActionEvent.CertificateLesson>()
        .transform {
            if (it.qrLessonId == lessonId) {
                emitAll(certificateLessonUseCase(it.qrLessonId).asResult())
            } else {
                //show error dialog invliad lesson id
            }
        }
        .onEach {
            if (it is Result.Success) {
                eventInvoker(LessonDetailActionEvent.Refresh)
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
                        isLoading = false
                    )
                }

                is Result.Loading -> {
                    machineInternalState.copy(
                        isLoading = true
                    )
                }
            }
        }

    override val outerNotifyScenarioActionFlow = merge(
        popBackFlow,
        navigateLessonCertificationQrFlow,
        navigateLessonInfoDetailFlow,
        deleteLessonFlow,
        updateLessonDeducted,
        recognizeQrFlow
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
            showDeleteLessonDialogFlow,
            hideNotifyLessonDeductedDialog,
            showNotifyLessonDeductedDialogFlow,
            certificateLessonFlow
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
                if (gwasuwonQr.type == GwasuwonQrType.LESSON_CERTIFICATION) {
                    (gwasuwonQr.data as? LessonCertificationData)?.lessonId?.let { lessonId ->
                        LessonDetailActionEvent.CertificateLesson(lessonId)

                    }
                }
            }
            null
        }

        else -> null
    }
}