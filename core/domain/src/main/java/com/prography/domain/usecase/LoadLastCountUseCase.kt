package com.prography.domain.usecase

import com.prography.utils.security.CryptoHelper
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by MyeongKi.
 */
class LoadLastCountUseCase(
    private val cryptoHelper: CryptoHelper
) {
    operator fun invoke(): Flow<Int> = flow {
        val lastCount = cryptoHelper.decryptContents()?.toInt()
        delay(1000L)
        emit(lastCount!!)
    }
}