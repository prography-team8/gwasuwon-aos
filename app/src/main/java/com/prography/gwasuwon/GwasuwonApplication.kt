package com.prography.gwasuwon

import android.app.Application
import android.content.Context
import com.prography.account.RootSocialLoginManager

/**
 * Created by MyeongKi.
 */
class GwasuwonApplication:Application() {
    init {
        this.also { instance = it }
    }

    override fun onCreate() {
        super.onCreate()
        RootSocialLoginManager.intialManager(this, AppContainer.socialLoginEventFlow)
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