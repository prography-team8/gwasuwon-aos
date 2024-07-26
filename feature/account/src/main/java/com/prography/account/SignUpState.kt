package com.prography.account

import com.prography.domain.account.model.AccountRole
import com.prography.ui.component.CommonDialogState
import com.prography.usm.state.MachineInternalState
import com.prography.usm.state.UiState

/**
 * Created by MyeongKi.
 */
data class SignUpMachineState(
    val signUpScreenType: SignUpScreenType,
    val isPersonalInformationAgreement: Boolean,
    val isGwasuwonServiceAgreement: Boolean,
    val roleType: AccountRole? = null,
    val isLoading: Boolean = false,
    val dialog: SignUpDialog = SignUpDialog.None
) : MachineInternalState<SignUpUiState> {
    override fun toUiState(): SignUpUiState {
        return when (signUpScreenType) {
            SignUpScreenType.AGREEMENT -> {
                SignUpUiState.Agreement(
                    isAllAgreement = isAllAgreement(),
                    isPersonalInformationAgreement = isPersonalInformationAgreement,
                    isGwasuwonServiceAgreement = isGwasuwonServiceAgreement,
                    isAvailableNextButton = isAllAgreement()
                )
            }

            SignUpScreenType.SELECT_ROLE -> {
                SignUpUiState.SelectRole(
                    roleType = roleType,
                    isLoading = isLoading,
                    dialog = dialog
                )
            }

            SignUpScreenType.COMPLETE -> {
                SignUpUiState.Complete
            }
        }
    }

    fun isAllAgreement(): Boolean {
        return isPersonalInformationAgreement && isGwasuwonServiceAgreement
    }
}

sealed interface SignUpUiState : UiState {
    data class Agreement(
        val isAllAgreement: Boolean,
        val isPersonalInformationAgreement: Boolean,
        val isGwasuwonServiceAgreement: Boolean,
        val isAvailableNextButton: Boolean
    ) : SignUpUiState

    data class SelectRole(
        val roleType: AccountRole?,
        val isLoading: Boolean,
        val dialog: SignUpDialog
    ) : SignUpUiState

    data object Complete : SignUpUiState
}

enum class SignUpScreenType(val page: Int) {
    AGREEMENT(1),
    SELECT_ROLE(2),
    COMPLETE(3)
    ;

    fun next(): SignUpScreenType? {
        return entries.find { (this.page + 1) == it.page }
    }
}

sealed interface SignUpDialog {
    data object None : SignUpDialog
    data class SignUpCommonDialog(
        val state: CommonDialogState
    ) : SignUpDialog
}