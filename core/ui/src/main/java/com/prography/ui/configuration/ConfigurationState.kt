package com.prography.ui.configuration

import com.prography.domain.configuration.ThemeType

/**
 * Created by MyeongKi.
 */
data class ConfigurationState(
    val colors: ColorsTheme,
) {
    companion object {
        fun create(themeType: ThemeType): ConfigurationState {
            return ConfigurationState(
                colors = when (themeType) {
                    ThemeType.LIGHT -> lightThemeColorsTheme()
                    ThemeType.DARK -> darkThemeColorsTheme()
                },
            )
        }
    }
}