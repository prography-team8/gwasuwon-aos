package com.prography.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.prography.ui.configuration.toColor

/**
 * Created by MyeongKi.
 */

@Composable
fun LoadingTransparentScreen() {
    Column(
        modifier = Modifier
            .pointerInput(Unit) { detectTapGestures() }
            .fillMaxSize()
            .background(Color.Transparent),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        LoadingItem()
    }
}

@Composable
fun LoadingItem() {
    CircularProgressIndicator(
        strokeWidth = 4.dp,
        modifier = Modifier
            .size(60.dp),
        color = GwasuwonConfigurationManager.colors.primaryNormal.toColor(),
        trackColor = GwasuwonConfigurationManager.colors.backgroundElevatedAlternative.toColor(),
    )
}