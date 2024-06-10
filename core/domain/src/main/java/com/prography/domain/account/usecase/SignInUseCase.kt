package com.prography.domain.account.usecase

import com.prography.domain.account.model.SocialLoginType
import com.prography.domain.account.repository.AccountRepository
import kotlinx.coroutines.flow.Flow

/**
 * Created by MyeongKi.
 */
class SignInUseCase(
    private val repository: AccountRepository
) {
    operator fun invoke(type: SocialLoginType, accessKey: String): Flow<Unit> = repository.signIn(type, accessKey)
}