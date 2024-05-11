package com.prography.domain.usecase

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by MyeongKi.
 */
class SaveCurrentCountUseCase {
    operator fun invoke(count: Int): Flow<Int> = flow {
        if (count < 0) {
            throw InvalidCount()
        }
        delay(1000L)
        emit(count)
    }

    class InvalidCount : Exception()
}