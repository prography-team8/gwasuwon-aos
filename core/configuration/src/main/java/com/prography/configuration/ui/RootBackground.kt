package com.prography.configuration.ui

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
import com.prography.configuration.ColorsTheme
import com.prography.configuration.R
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
            color = GwasuwonConfigurationManager.colors.backgroundRegularNormal.toColor()
        )

        Surface(
            modifier = Modifier
                .background(GwasuwonConfigurationManager.colors.backgroundRegularNormal.toColor())
                .fillMaxSize()
                .padding(
                    horizontal = dimensionResource(id = R.dimen.common_large_padding)
                ),
            color = GwasuwonConfigurationManager.colors.backgroundRegularNormal.toColor()
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