package com.prography.gwasuwon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.prography.account.RootSocialLoginManager
import com.prography.configuration.ui.ConfigurationStateViewModel
import com.prography.configuration.ui.RootBackground
import com.prography.domain.account.SocialLoginEvent
import com.prography.gwasuwon.navigate.GwasuwonNavGraph
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val configurationViewModel: ConfigurationStateViewModel = viewModel(
                factory = ConfigurationStateViewModel.provideFactory(
                    eventFlow = AppContainer.configurationEvent,
                    themePreference = AppContainer.themePreference
                )
            )
            RootBackground(configurationViewModel) {
                GwasuwonNavGraph {
                    moveTaskToBack(true)
                }
            }
        }
        observeEvent()
    }

    private fun observeEvent() {
        AppContainer.socialLoginEventFlow.onEach {
            when(it){
                is SocialLoginEvent.RequestSocialLoginAccessKey->{
                    RootSocialLoginManager.requestAccessToken(it.type, this, lifecycleScope)
                }
                else->Unit
            }
        }.launchIn(lifecycleScope)
    }
}
