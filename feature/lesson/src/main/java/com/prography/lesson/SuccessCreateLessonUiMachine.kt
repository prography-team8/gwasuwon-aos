package com.prography.lesson

import NavigationEvent
import com.prography.domain.lesson.usecase.LoadLessonContractUrlUseCase
import com.prography.usm.holder.UiStateMachine
import com.prography.usm.result.Result
import com.prography.usm.result.asResult
import com.prography.utils.clipboar.ClipboardHelper
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
class SuccessCreateLessonUiMachine(
    coroutineScope: CoroutineScope,
    lessonId: Long,
    navigateFlow: MutableSharedFlow<NavigationEvent>,
    clipboardHelper: ClipboardHelper,
    loadContractUrlUseCase: LoadLessonContractUrlUseCase
) : UiStateMachine<
        SuccessCreateLessonUiState,
        SuccessCreateLessonMachineState,
        SuccessCreateLessonActionEvent,
        SuccessCreateLessonIntent>(coroutineScope) {
    override var machineInternalState: SuccessCreateLessonMachineState = SuccessCreateLessonMachineState()

    private val loadLessonContractUrlFlow = actionFlow
        .filterIsInstance<SuccessCreateLessonActionEvent.LoadLessonContractUrl>()
        .transform {
            emitAll(loadContractUrlUseCase(lessonId).asResult())
        }
        .map {
            when (it) {
                is Result.Success -> {
                    machineInternalState.copy(
                        contractUrl = it.data,
                        isLoading = false
                    )
                }

                is Result.Loading -> {
                    machineInternalState.copy(
                        isLoading = true
                    )
                }

                is Result.Error -> {
                    machineInternalState.copy(
                        isLoading = false
                    )
                }
            }
        }
    private val hideDialogFlow = actionFlow
        .filterIsInstance<SuccessCreateLessonActionEvent.HideDialog>()
        .map {
            machineInternalState.copy(
                dialog = SuccessCreateLessonDialog.None
            )
        }
    private val copyLessonContractQr = actionFlow
        .filterIsInstance<SuccessCreateLessonActionEvent.CopyLessonContractQr>()
        .onEach {
            clipboardHelper.copyToClipboard(machineInternalState.contractUrl)
        }
        .map {
            machineInternalState.copy(
                dialog = SuccessCreateLessonDialog.SuccessCopy
            )
        }
    private val navigateLessonDetailFlow = actionFlow
        .filterIsInstance<SuccessCreateLessonActionEvent.NavigateLessonDetail>()
        .onEach {
            navigateFlow.emit(NavigationEvent.PopBack)
            navigateFlow.emit(NavigationEvent.NavigateLessonDetailRoute(lessonId = lessonId))

        }
    override val outerNotifyScenarioActionFlow = merge(
        navigateLessonDetailFlow,
    )

    init {
        initMachine()
        eventInvoker(SuccessCreateLessonActionEvent.LoadLessonContractUrl)
    }

    override fun mergeStateChangeScenarioActionsFlow(): Flow<SuccessCreateLessonMachineState> {
        return merge(
            loadLessonContractUrlFlow,
            copyLessonContractQr,
            hideDialogFlow
        )
    }
}
