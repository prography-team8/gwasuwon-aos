package com.prography.account

import com.prography.domain.account.model.AccountType
import com.prography.usm.state.MachineInternalState
import com.prography.usm.state.UiState

/**
 * Created by MyeongKi.
 */
data class SignUpMachineState(
    val signUpScreenType: SignUpScreenType,
    val isPersonalInformationAgreement: Boolean,
    val isGwasuwonServiceAgreement: Boolean,
    val accountType: AccountType? = null,
    val isLoading: Boolean = false
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
                SignUpUiState.SelectRole(accountType = accountType)
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
        val accountType: AccountType? = null
    ) : SignUpUiState
}

enum class SignUpScreenType(val page: Int) {
    AGREEMENT(1),
    SELECT_ROLE(2)
    ;

    fun next(): SignUpScreenType? {
        return entries.find { (this.page + 1) == it.page }
    }
}