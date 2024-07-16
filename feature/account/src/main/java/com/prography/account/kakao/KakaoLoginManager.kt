package com.prography.account.kakao

import android.app.Activity
import android.app.Application
import android.util.Log
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.user.UserApiClient
import com.prography.domain.account.SocialLoginEvent
import com.prography.domain.account.model.SocialLoginType
import com.prography.login.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

/**
 * Created by MyeongKi.
 */
internal object KakaoLoginManager {
    private val TAG = this::class.java.name
    private lateinit var socialLoginEventFlow: MutableSharedFlow<SocialLoginEvent>
    fun intialManager(application: Application, socialLoginEventFlow: MutableSharedFlow<SocialLoginEvent>) {
        KakaoSdk.init(application, application.getString(R.string.NATIVE_APP_KEY))
        this.socialLoginEventFlow = socialLoginEventFlow
    }

    fun requestKakaoAccessToken(activity: Activity, scope: CoroutineScope) {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(activity)) {
            UserApiClient.instance.loginWithKakaoTalk(activity, callback = getKakaoLoginCallback(scope))
        } else {
            UserApiClient.instance.loginWithKakaoAccount(activity, callback = getKakaoLoginCallback(scope))
        }
    }
    //카카오 실패한 경우에 다이얼로그 노출 필요
    private fun getKakaoLoginCallback(scope: CoroutineScope): (token: OAuthToken?, error: Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e(TAG, "로그인 실패", error)
        } else if (token != null) {
            Log.i(TAG, "로그인 성공 ${token.accessToken}")
            scope.launch {
                socialLoginEventFlow.emit(
                    SocialLoginEvent.GetOnSuccessSocialLoginAccessKey(
                        type = SocialLoginType.KAKAO,
                        accessKey = token.accessToken
                    )
                )
            }
        }
    }
}