package com.prography.domain.preference

import com.prography.domain.configuration.ThemeType

/**
 * Created by MyeongKi.
 */
interface ThemePreference {
    fun setThemeName(themeType: ThemeType)
    fun getThemeType(): ThemeType
}