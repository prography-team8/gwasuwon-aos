package com.prography.domain.configuration

/**
 * Created by MyeongKi.
 */
enum class ThemeType {
    DARK,
    LIGHT;

    companion object {
        fun toFind(name: String?): ThemeType {
            entries.forEach {
                if (it.name == name) {
                    return it
                }
            }
            return DARK
        }
    }
}