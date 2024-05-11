package com.prography.gwasuwon

import android.app.Application
import android.content.Context

/**
 * Created by MyeongKi.
 */
class GwasuwonApplication:Application() {
    init {
        this.also { instance = it }
    }
    companion object {
        private var instance: GwasuwonApplication? = null
        val currentApplication
            get() = instance!!

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }
}