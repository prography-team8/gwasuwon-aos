package com.prography.account

import NavigationEvent
import com.prography.domain.account.SocialLoginEvent
import com.prography.domain.account.usecase.SignInUseCase
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

/**
 * Created by MyeongKi.
 */
class SignInUiMachine(
    coroutineScope: CoroutineScope,
    navigateFlow: MutableSharedFlow<NavigationEvent>,
    socialLoginFlow: MutableSharedFlow<SocialLoginEvent>,
    signInUseCase: SignInUseCase,
) : UiStateMachine<SignInUiState, SignInMachineState, SignInActionEvent, SignInIntent>(
    coroutineScope
) {
    private val accountOuterToActionFlow = socialLoginFlow.mapNotNull {
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
    override val fromOutsideActionFlowMerged: Flow<SignInActionEvent> = merge(accountOuterToActionFlow)

    override var machineInternalState: SignInMachineState = SignInMachineState(
        isLoading = false
    )

    private val requestSignInFlow = actionFlow
        .filterIsInstance<SignInActionEvent.RequestSignIn>()
        .transform {
            emitAll(signInUseCase(it.type, it.accessKey).asResult())
        }
        .onEach {
            when (it) {
                is Result.Error -> {
                    //FIXME error code에 따라서 signUp page로 바로 랜딩할지 또는 회원가입 확인 다이얼로그를 노출하고 랜딩으로 이동할지 결정이 필요.
                }

                is Result.Success<Unit> -> {
                    eventInvoker(SignInActionEvent.NavigateLessonRoute)
                }

                else -> Unit
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

                is Result.Success<Unit> -> {
                    machineInternalState.copy(isLoading = false)
                }
            }
        }
    private val showFailSocialLoginFailFlow = actionFlow
        .filterIsInstance<SignInActionEvent.ShowFailSocialLoginFail>()
        .map {
            machineInternalState
        }
    private val navigateLessonRouteFlow = actionFlow
        .filterIsInstance<SignInActionEvent.NavigateLessonRoute>()
        .onEach {
            navigateFlow.emit(NavigationEvent.NavigateLessonRoute)
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
            showFailSocialLoginFailFlow
        )
    }
}