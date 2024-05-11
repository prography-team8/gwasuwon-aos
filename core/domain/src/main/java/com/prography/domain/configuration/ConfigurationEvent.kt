package com.prography.domain.configuration

/**
 * Created by MyeongKi.
 */
sealed interface ConfigurationEvent {
    data class ChangeTheme(val themeType: ThemeType) : ConfigurationEvent
}