package com.prography.domain.preference

import android.app.Application
import android.content.Context.MODE_PRIVATE

/**
 * Created by MyeongKi.
 */

const val SP_NAME = "gwasuwon"
fun Application.putLong(key: String, value: Long) {
    getSpEditor().putLong(key, value).apply()
}
fun Application.getLong(key:String, default:Long):Long{
    return getSp().getLong(key, default)
}
fun Application.putInt(key: String, value: Int) {
    getSpEditor().putInt(key, value).apply()
}

fun Application.getInt(key: String, default: Int): Int {
    return  getSp().getInt(key, default)
}

fun Application.putString(key: String, value: String) {
    getSpEditor().putString(key, value).apply()
}

fun Application.getString(key: String): String? {
    return  getSp().getString(key, null)
}

fun Application.putBool(key: String, value: Boolean) {
    getSpEditor().putBoolean(key, value).apply()
}

fun Application.getBool(key: String, default: Boolean): Boolean {
    return getSp().getBoolean(key, default)
}

private fun Application.getSp() = getSharedPreferences(SP_NAME, MODE_PRIVATE)

private fun Application.getSpEditor() = getSp().edit()