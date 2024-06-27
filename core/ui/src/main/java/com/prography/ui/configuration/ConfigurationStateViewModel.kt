package com.prography.ui.configuration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.prography.domain.configuration.ConfigurationEvent
import com.prography.domain.preference.ThemePreference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn

/**
 * Created by MyeongKi.
 */
class ConfigurationStateViewModel(
    themePreference: ThemePreference,
    eventFlow: Flow<ConfigurationEvent>,
) : ViewModel() {
    private var _configurationState = ConfigurationState(
        colors = themePreference.getThemeType().toColors(),
    )
    private val changeThemeFlow = eventFlow
        .filterIsInstance<ConfigurationEvent.ChangeTheme>()
        .onEach {
            themePreference.setThemeName(it.themeType)
        }
        .map {
            _configurationState.copy(
                colors = it.themeType.toColors()
            )
        }
    val configurationState = merge(
        changeThemeFlow,
    )
        .onEach { _configurationState = it }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            _configurationState
        )

    companion object {
        fun provideFactory(
            themePreference: ThemePreference,
            eventFlow: Flow<ConfigurationEvent>,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ConfigurationStateViewModel(
                    themePreference, eventFlow
                ) as T
            }
        }
    }
}
