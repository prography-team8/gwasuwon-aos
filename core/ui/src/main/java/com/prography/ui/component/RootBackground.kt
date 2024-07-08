package com.prography.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.prography.ui.configuration.ColorsTheme
import com.prography.ui.R
import com.prography.ui.configuration.toColor

/**
 * Created by MyeongKi.
 */
val LocalColors = staticCompositionLocalOf {
    ColorsTheme()
}

@Composable
fun RootBackground(viewModel: com.prography.ui.configuration.ConfigurationStateViewModel, content: @Composable () -> Unit) {
    val state = viewModel.configurationState.collectAsState().value
    GwasuwonConfiguration(configurationState = state) {
        val systemUiController = rememberSystemUiController()
        systemUiController.setSystemBarsColor(
            color = GwasuwonConfigurationManager.colors.backgroundRegularNormal.toColor()
        )

        Surface(
            modifier = Modifier
                .background(GwasuwonConfigurationManager.colors.backgroundRegularNormal.toColor())
                .fillMaxSize(),
            color = GwasuwonConfigurationManager.colors.backgroundRegularNormal.toColor()
        ) {
            content()
        }
    }
}

@Composable
fun GwasuwonConfiguration(
    configurationState: com.prography.ui.configuration.ConfigurationState,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalColors provides configurationState.colors,
        content = content
    )
}

object GwasuwonConfigurationManager {
    val colors: ColorsTheme
        @Composable
        get() = LocalColors.current
}