package com.prography.gwasuwon

import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.prography.account.RootSocialLoginManager
import com.prography.domain.account.SocialLoginEvent
import com.prography.gwasuwon.navigate.GwasuwonNavGraph
import com.prography.qr.ShowQrScannerSubscriber
import com.prography.ui.component.RootBackground
import com.prography.ui.configuration.ConfigurationStateViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class MainActivity : ComponentActivity() {
    private lateinit var qrScannerSubscriber: ShowQrScannerSubscriber
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        qrScannerSubscriber = ShowQrScannerSubscriber(this, AppContainer.qrEventFlow)
        setContent {
            val configurationViewModel: ConfigurationStateViewModel = viewModel(
                factory = ConfigurationStateViewModel.provideFactory(
                    eventFlow = AppContainer.configurationEvent,
                    themePreference = AppContainer.themePreference
                )
            )
            RootBackground(configurationViewModel) {
                GwasuwonNavGraph(
                    accountInfoManager = AppContainer.accountInfoManager,
                    navigateWeb = {
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.setData(Uri.parse(it))
                        startActivity(intent)
                    },
                    onEmptyBackStack = {
                        moveTaskToBack(true)
                    }
                )
            }
        }
        observeEvent()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }

    private fun observeEvent() {
        qrScannerSubscriber.observeEvent()
        AppContainer.socialLoginEventFlow.onEach {
            when (it) {
                is SocialLoginEvent.RequestSocialLoginAccessKey -> {
                    RootSocialLoginManager.requestAccessToken(it.type, this, lifecycleScope)
                }

                else -> Unit
            }
        }.launchIn(lifecycleScope)
    }
}
