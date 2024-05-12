package com.prography.gwasuwon

import com.prography.domain.configuration.ConfigurationEvent
import com.prography.domain.preference.ThemePreferenceImpl
import com.prography.domain.usecase.LoadLastCountUseCase
import com.prography.domain.usecase.SaveCurrentCountUseCase
import com.prography.utils.security.SampleCryptoHelper
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

/**
 * Created by MyeongKi.
 */
object AppContainer {
    val configurationEvent: SharedFlow<ConfigurationEvent> = MutableSharedFlow()

    val sampleCryptoHelper by lazy {
        SampleCryptoHelper(
            GwasuwonApplication.currentApplication
        )
    }
    val themePreference by lazy {
        ThemePreferenceImpl(GwasuwonApplication.currentApplication)
    }
    val sampleCountUseCase by lazy {
        SaveCurrentCountUseCase(sampleCryptoHelper)
    }
    val sampleLoadUseCase by lazy {
        LoadLastCountUseCase(sampleCryptoHelper)
    }
}