package com.prography.configuration

import androidx.annotation.ColorRes
import com.prography.domain.configuration.ThemeType

/**
 * Created by MyeongKi.
 */
data class ColorsTheme(
    @ColorRes val semanticPrimaryNormal:Int =R.color.primary_normal,
    @ColorRes val semanticStaticBlack:Int = R.color.black,
    @ColorRes val kakaoColor:Int = R.color.kakao,
    @ColorRes val semanticBackgroundNormalNormal:Int = 0,
)

private fun defaultColorsTheme() = ColorsTheme()

fun darkThemeColorsTheme() = defaultColorsTheme().copy(
    semanticBackgroundNormalNormal = R.color.black,
)

fun lightThemeColorsTheme() = defaultColorsTheme().copy(
    semanticBackgroundNormalNormal = R.color.white,
)



fun ThemeType.toColors(): ColorsTheme = when (this) {
    ThemeType.DARK -> {
        darkThemeColorsTheme()
    }

    ThemeType.LIGHT -> {
        lightThemeColorsTheme()
    }
}