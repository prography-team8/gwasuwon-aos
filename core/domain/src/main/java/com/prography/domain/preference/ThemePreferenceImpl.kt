package com.prography.domain.preference

import android.app.Application
import com.prography.domain.configuration.ThemeType

/**
 * Created by MyeongKi.
 */
class ThemePreferenceImpl(private val context: Application) : ThemePreference {

    override fun setThemeName(themeType: ThemeType) {
        context.putString(KEY_THEME_TYPE, themeType.name)
    }

    override fun getThemeType(): ThemeType {
        return ThemeType.toFind(context.getString(KEY_THEME_TYPE))
    }

    companion object {
        private const val KEY_THEME_TYPE = "themeType"
    }
}