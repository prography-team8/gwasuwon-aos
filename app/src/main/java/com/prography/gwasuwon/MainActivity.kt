package com.prography.gwasuwon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.prography.configuration.ui.ConfigurationStateViewModel
import com.prography.configuration.ui.RootBackground
import com.prography.usm_sample.SampleCountViewModel
import com.prography.usm_sample.compose.SampleCountRoute

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val configurationViewModel:ConfigurationStateViewModel = viewModel(
                factory = ConfigurationStateViewModel.provideFactory(
                    eventFlow = AppContainer.configurationEvent,
                    themePreference = AppContainer.themePreference
                )
            )
            RootBackground(configurationViewModel) {
                SampleCountRoute(
                    viewModel = viewModel(
                        factory = SampleCountViewModel.provideFactory(
                            saveCurrentCountUseCase = AppContainer.sampleCountUseCase,
                            loadLastCountUseCase = AppContainer.sampleLoadUseCase
                        )
                    )
                )
            }
        }
    }
}
