package com.prography.configuration.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.prography.configuration.ColorsTheme
import com.prography.configuration.toColor

/**
 * Created by MyeongKi.
 */
val LocalColors = staticCompositionLocalOf {
    ColorsTheme()
}

@Composable
fun RootBackground(viewModel: ConfigurationStateViewModel, content: @Composable () -> Unit) {
    val state = viewModel.configurationState.collectAsState().value
    GwasuwonConfiguration(configurationState = state) {
        val systemUiController = rememberSystemUiController()
        systemUiController.setSystemBarsColor(
            color = GwasuwonConfigurationManager.colors.semanticBackgroundNormalNormal.toColor()
        )

        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = GwasuwonConfigurationManager.colors.semanticBackgroundNormalNormal.toColor()
        ) {
            content()
        }
    }
}
@Composable
fun GwasuwonConfiguration(
    configurationState: ConfigurationState,
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