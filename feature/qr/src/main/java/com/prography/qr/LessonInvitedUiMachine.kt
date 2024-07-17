package com.prography.qr

import NavigationEvent
import com.prography.domain.lesson.usecase.ParticipateLessonUseCase
import com.prography.domain.qr.CommonQrEvent
import com.prography.qr.domain.model.GwasuwonQr
import com.prography.qr.domain.model.GwasuwonQrType
import com.prography.qr.domain.model.InviteStudentData
import com.prography.usm.holder.UiStateMachine
import com.prography.usm.result.Result
import com.prography.usm.result.asResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.transform
import kotlinx.serialization.json.Json

/**
 * Created by MyeongKi.
 */
class LessonInvitedUiMachine(
    coroutineScope: CoroutineScope,
    navigateFlow: MutableSharedFlow<NavigationEvent>,
    commonQrFlow: MutableSharedFlow<CommonQrEvent>,
    participateLessonUseCase: ParticipateLessonUseCase
) : UiStateMachine<
        LessonInvitedUiState,
        LessonInvitedMachineState,
        LessonInvitedActionEvent,
        LessonInvitedIntent>(
    coroutineScope,
    commonQrFlow.toLessonInvitedAction()
) {

    override var machineInternalState: LessonInvitedMachineState = LessonInvitedMachineState()

    private val participateLessonFlow = actionFlow
        .filterIsInstance<LessonInvitedActionEvent.ParticipateLesson>()
        .transform {
            emitAll(participateLessonUseCase(it.lessonId).asResult())
        }
        .map {
            when (it) {
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

                is Result.Success -> {
                    machineInternalState.copy(
                        lessonId = it.data.lessonId,
                        isLoading = false,
                        isParticipateLesson = true
                    )
                }
            }
        }

    private val startQrRecognitionFlow = actionFlow
        .filterIsInstance<LessonInvitedActionEvent.StartQrRecognition>()
        .onEach {
            commonQrFlow.emit(CommonQrEvent.RequestQrScan)
        }

    private val navigateLessonDetailFlow = actionFlow
        .filterIsInstance<LessonInvitedActionEvent.NavigateLessonDetail>()
        .onEach {
            machineInternalState.lessonId?.let {
                navigateFlow.emit(NavigationEvent.NavigateLessonDeatailRoute(it))
            }
        }

    override val outerNotifyScenarioActionFlow = merge(
        startQrRecognitionFlow,
        navigateLessonDetailFlow
    )

    init {
        initMachine()
    }

    override fun mergeStateChangeScenarioActionsFlow(): Flow<LessonInvitedMachineState> {
        return merge(participateLessonFlow)
    }
}

private fun MutableSharedFlow<CommonQrEvent>.toLessonInvitedAction(): Flow<LessonInvitedActionEvent> = this.mapNotNull {
    when (it) {
        is CommonQrEvent.GetOnSuccessQr -> {
            val jsonString: GwasuwonQr? = try {
                Json.decodeFromString(GwasuwonQr.serializer(), it.raw)
            } catch (e: Exception) {
                null
            }
            jsonString?.let { gwasuwonQr ->
                if (gwasuwonQr.type == GwasuwonQrType.INVITE_STUDENT) {
                    (gwasuwonQr.data as? InviteStudentData)?.lessonId?.let { lessonId ->
                        LessonInvitedActionEvent.ParticipateLesson(lessonId)

                    }
                }
            }
            null
        }

        else -> null
    }
}