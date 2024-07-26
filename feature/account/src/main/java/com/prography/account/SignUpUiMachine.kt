package com.prography.account

import NavigationEvent
import com.prography.domain.account.model.AccountRole
import com.prography.domain.account.request.SignUpRequestOption
import com.prography.domain.account.usecase.SignUpUseCase
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
class SignUpUiMachine(
    coroutineScope: CoroutineScope,
    navigateFlow: MutableSharedFlow<NavigationEvent>,
    signUpUseCase: SignUpUseCase,
) : UiStateMachine<SignUpUiState, SignUpMachineState, SignUpActionEvent, SignUpIntent>(
    coroutineScope,
) {

    override var machineInternalState: SignUpMachineState = SignUpMachineState(
        signUpScreenType = SignUpScreenType.AGREEMENT,
        isPersonalInformationAgreement = false,
        isGwasuwonServiceAgreement = false
    )
    private val toggleAllAgreementFlow = actionFlow
        .filterIsInstance<SignUpActionEvent.ToggleAllAgreement>()
        .map {
            machineInternalState.copy(
                isPersonalInformationAgreement = machineInternalState.isAllAgreement().not(),
                isGwasuwonServiceAgreement = machineInternalState.isAllAgreement().not()
            )
        }
    private val togglePersonalInformationAgreementFlow = actionFlow
        .filterIsInstance<SignUpActionEvent.TogglePersonalInformationAgreement>()
        .map {
            machineInternalState.copy(
                isPersonalInformationAgreement = machineInternalState.isPersonalInformationAgreement.not(),
            )
        }
    private val toggleGwasuwonServiceAgreementFlow = actionFlow
        .filterIsInstance<SignUpActionEvent.ToggleGwasuwonServiceAgreement>()
        .map {
            machineInternalState.copy(
                isGwasuwonServiceAgreement = machineInternalState.isGwasuwonServiceAgreement.not(),
            )
        }
    private val goToNextSignUpPageFlow = actionFlow
        .filterIsInstance<SignUpActionEvent.GoToNextSignUpPage>()
        .map {
            machineInternalState.copy(
                signUpScreenType = machineInternalState.signUpScreenType.next() ?: machineInternalState.signUpScreenType,
            )
        }
    private val hideDialogFlow = actionFlow
        .filterIsInstance<SignUpActionEvent.HideDialog>()
        .map {
            machineInternalState.copy(
                dialog = SignUpDialog.None
            )
        }
    private val selectTeacherFlow = actionFlow
        .filterIsInstance<SignUpActionEvent.SelectTeacher>()
        .map {
            machineInternalState.copy(
                roleType = AccountRole.TEACHER
            )
        }
        .onEach {
            eventInvoker(SignUpActionEvent.RequestSignUp)
        }
    private val selectStudentFlow = actionFlow
        .filterIsInstance<SignUpActionEvent.SelectStudent>()
        .map {
            machineInternalState.copy(
                roleType = AccountRole.STUDENT
            )
        }
        .onEach {
            eventInvoker(SignUpActionEvent.RequestSignUp)
        }
    private val requestSignUpFlow = actionFlow
        .filterIsInstance<SignUpActionEvent.RequestSignUp>()
        .mapNotNull { machineInternalState.roleType }
        .transform {
            emitAll(
                signUpUseCase(
                    SignUpRequestOption(
                        roleType = it,
                        privacyPolicyAgreement = machineInternalState.isPersonalInformationAgreement,
                        termsOfServiceAgreement = machineInternalState.isGwasuwonServiceAgreement
                    )
                ).asResult()
            )
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
                        dialog = SignUpDialog.SignUpCommonDialog(dialog)
                    )
                }

                is Result.Loading -> {
                    machineInternalState.copy(isLoading = true)
                }

                is Result.Success -> {
                    machineInternalState.copy(
                        isLoading = false,
                        signUpScreenType = SignUpScreenType.COMPLETE
                    )
                }
            }
        }

    private val navigateLessonRouteFlow = actionFlow
        .filterIsInstance<SignUpActionEvent.NavigateHome>()
        .onEach {
            if (machineInternalState.roleType == AccountRole.STUDENT)
                navigateFlow.emit(NavigationEvent.NavigateLessonInvitedRoute)
            else
                navigateFlow.emit(NavigationEvent.NavigateLessonsRoute)
        }

    private val showAgreementPage = actionFlow
        .filterIsInstance<SignUpActionEvent.ShowAgreementPage>()
        .onEach {
            navigateFlow.emit(NavigationEvent.NavigateWeb(it.url))
        }

    override val outerNotifyScenarioActionFlow = merge(
        navigateLessonRouteFlow,
        showAgreementPage
    )

    init {
        initMachine()
    }

    override fun mergeStateChangeScenarioActionsFlow(): Flow<SignUpMachineState> {
        return merge(
            toggleAllAgreementFlow,
            toggleGwasuwonServiceAgreementFlow,
            togglePersonalInformationAgreementFlow,
            goToNextSignUpPageFlow,
            selectStudentFlow,
            selectTeacherFlow,
            requestSignUpFlow,
            hideDialogFlow
        )
    }
}
