package com.prography.domain.account.usecase

import com.prography.utils.security.CryptoHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by MyeongKi.
 */
class SignOutUseCase(
    private val jwtCryptoHelper: CryptoHelper
) {
    operator fun invoke(
    ): Flow<Unit> = flow {
        jwtCryptoHelper.encryptContentsAndStore("")
        emit(Unit)
    }
}