package com.prography.gwasuwon

import com.prography.domain.usecase.SaveCurrentCountUseCase

/**
 * Created by MyeongKi.
 */
object AppContainer {
    val sampleCountUseCase: SaveCurrentCountUseCase by lazy {
        SaveCurrentCountUseCase()
    }
}