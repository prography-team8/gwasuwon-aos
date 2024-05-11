package com.prography.configuration.ui

import com.prography.configuration.ColorsTheme
import com.prography.configuration.darkThemeColorsTheme
import com.prography.configuration.lightThemeColorsTheme
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