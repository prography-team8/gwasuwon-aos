package com.prography.domain.usecase

import com.prography.utils.security.CryptoHelper
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by MyeongKi.
 */
class SaveCurrentCountUseCase(
    private val cryptoHelper: CryptoHelper
) {
    operator fun invoke(count: Int): Flow<Int> = flow {
        if (count < 0) {
            throw InvalidCount()
        }
        cryptoHelper.encryptContentsAndStore(count.toString())
        delay(1000L)
        emit(count)
    }

    class InvalidCount : Exception()
}