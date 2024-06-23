package com.prography.domain.account.usecase

import com.prography.domain.account.AccountInfoManager
import com.prography.domain.account.model.AccountInfo
import com.prography.domain.account.model.SocialLoginType
import com.prography.domain.account.repository.AccountRepository
import com.prography.domain.account.request.SignInRequestOption
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

/**
 * Created by MyeongKi.
 */
class SignInUseCase(
    private val repository: AccountRepository,
    private val accountInfoManager: AccountInfoManager
) {
    operator fun invoke(
        type: SocialLoginType,
        accessKey: String
    ): Flow<AccountInfo> = repository.signIn(
        SignInRequestOption(
            type = type,
            accessKey = accessKey
        )
    ).onEach {
        accountInfoManager.update(it)
    }
}