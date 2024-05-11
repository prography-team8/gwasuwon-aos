package com.prography.gwasuwon

import com.prography.domain.configuration.ConfigurationEvent
import com.prography.domain.preference.ThemePreferenceImpl
import com.prography.domain.usecase.SaveCurrentCountUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

/**
 * Created by MyeongKi.
 */
object AppContainer {
    val themePreference by lazy {
        ThemePreferenceImpl(GwasuwonApplication.currentApplication)
    }
    val configurationEvent: SharedFlow<ConfigurationEvent> = MutableSharedFlow()
    val sampleCountUseCase: SaveCurrentCountUseCase by lazy {
        SaveCurrentCountUseCase()
    }
}