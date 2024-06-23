package com.prography.domain.account.usecase

import com.prography.domain.account.AccountInfoManager
import com.prography.domain.account.model.AccountInfo
import com.prography.domain.account.repository.AccountRepository
import com.prography.domain.account.request.SignUpRequestOption
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

/**
 * Created by MyeongKi.
 */
class SignUpUseCase(
    private val repository: AccountRepository,
    private val accountInfoManager: AccountInfoManager
) {
    operator fun invoke(
        requestOption: SignUpRequestOption
    ): Flow<AccountInfo> = repository.signUp(
        requestOption
    ).onEach {
        accountInfoManager.update(it)
    }
}