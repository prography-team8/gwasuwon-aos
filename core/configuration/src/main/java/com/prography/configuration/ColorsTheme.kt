package com.prography.configuration

import androidx.annotation.ColorRes
import com.prography.domain.configuration.ThemeType

/**
 * Created by MyeongKi.
 */
data class ColorsTheme(
    @ColorRes val backgroundColor: Int = 0,
    @ColorRes val textColor: Int = 0
)

private fun defaultColorsTheme() = ColorsTheme()

fun darkThemeColorsTheme() = defaultColorsTheme().copy(
    backgroundColor = R.color.black,
    textColor = R.color.white
)

fun lightThemeColorsTheme() = defaultColorsTheme().copy(
    backgroundColor = R.color.white,
    textColor = R.color.black
)



fun ThemeType.toColors(): ColorsTheme = when (this) {
    ThemeType.DARK -> {
        darkThemeColorsTheme()
    }

    ThemeType.LIGHT -> {
        lightThemeColorsTheme()
    }
}