package com.prography.account

import NavigationEvent
import com.prography.domain.account.SocialLoginEvent
import com.prography.domain.account.model.AccountStatus
import com.prography.domain.account.request.SignInRequestOption
import com.prography.domain.account.usecase.SignInUseCase
import com.prography.ui.component.CommonDialogState
import com.prography.usm.holder.UiStateMachine
import com.prography.usm.result.Result
import com.prography.usm.result.asResult
import com.prography.utils.network.NetworkUnavailableException
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

/**
 * Created by MyeongKi.
 */
class SignInUiMachine(
    coroutineScope: CoroutineScope,
    navigateFlow: MutableSharedFlow<NavigationEvent>,
    socialLoginFlow: MutableSharedFlow<SocialLoginEvent>,
    signInUseCase: SignInUseCase,
) : UiStateMachine<SignInUiState, SignInMachineState, SignInActionEvent, SignInIntent>(
    coroutineScope,
    merge(socialLoginFlow.toSignUpAction())
) {
    override var machineInternalState: SignInMachineState = SignInMachineState(
        isLoading = false,
        dialog = SignInDialog.None
    )

    private val requestSignInFlow = actionFlow
        .filterIsInstance<SignInActionEvent.RequestSignIn>()
        .transform {
            emitAll(
                signInUseCase(
                    SignInRequestOption(it.type, it.accessKey)
                ).asResult()
            )
        }
        .onEach {
            when (it) {
                is Result.Success -> {
                    if (it.data.status == AccountStatus.ACTIVE) {
                        eventInvoker(SignInActionEvent.NavigateLessonRoute)
                    } else {
                        eventInvoker(SignInActionEvent.NavigateSignUpRoute)
                    }
                }

                else -> Unit
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
                        dialog = SignInDialog.SignInCommonDialog(dialog)
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

    private val showFailSocialLoginFailFlow = actionFlow
        .filterIsInstance<SignInActionEvent.ShowFailSocialLoginFail>()
        .map {
            machineInternalState.copy(
                dialog = SignInDialog.SignInCommonDialog(
                    CommonDialogState.UnknownError
                )
            )
        }
    private val hideDialogFlow = actionFlow
        .filterIsInstance<SignInActionEvent.HideDialog>()
        .map {
            machineInternalState.copy(
                dialog = SignInDialog.None
            )
        }
    private val navigateLessonRouteFlow = actionFlow
        .filterIsInstance<SignInActionEvent.NavigateLessonRoute>()
        .onEach {
            navigateFlow.emit(NavigationEvent.NavigateLessonsRoute)
        }
    private val navigateSignUpRouteFlow = actionFlow
        .filterIsInstance<SignInActionEvent.NavigateSignUpRoute>()
        .onEach {
            navigateFlow.emit(NavigationEvent.NavigateSignUpRoute)
        }
    private val requestSocialLoginAccessKeyFlow = actionFlow
        .filterIsInstance<SignInActionEvent.RequestSocialLoginAccessKey>()
        .onEach {
            socialLoginFlow.emit(SocialLoginEvent.RequestSocialLoginAccessKey(it.type))
        }
    override val outerNotifyScenarioActionFlow = merge(
        navigateLessonRouteFlow,
        navigateSignUpRouteFlow,
        requestSocialLoginAccessKeyFlow
    )

    init {
        initMachine()
    }

    override fun mergeStateChangeScenarioActionsFlow(): Flow<SignInMachineState> {
        return merge(
            requestSignInFlow,
            showFailSocialLoginFailFlow,
            hideDialogFlow
        )
    }
}

private fun MutableSharedFlow<SocialLoginEvent>.toSignUpAction(): Flow<SignInActionEvent> = this.mapNotNull {
    when (it) {
        is SocialLoginEvent.GetOnSuccessSocialLoginAccessKey -> {
            SignInActionEvent.RequestSignIn(type = it.type, accessKey = it.accessKey)
        }

        is SocialLoginEvent.GetOnFailSocialLoginAccessKey -> {
            SignInActionEvent.ShowFailSocialLoginFail(type = it.type)
        }

        else -> null
    }
}