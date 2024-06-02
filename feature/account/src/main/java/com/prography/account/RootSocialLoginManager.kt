package com.prography.account

import android.app.Activity
import android.app.Application
import com.prography.account.kakao.KakaoLoginManager
import com.prography.domain.account.SocialLoginEvent
import com.prography.domain.account.model.SocialLoginType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow

/**
 * Created by MyeongKi.
 */
object RootSocialLoginManager {
    fun intialManager(application: Application, socialLoginEventFlow: MutableSharedFlow<SocialLoginEvent>) {
        KakaoLoginManager.intialManager(application, socialLoginEventFlow)
    }

    fun requestAccessToken(type: SocialLoginType, activity: Activity, scope: CoroutineScope) {
        when (type) {
            SocialLoginType.KAKAO -> {
                KakaoLoginManager.requestKakaoAccessToken(activity, scope)
            }
        }
    }
}