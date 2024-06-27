package com.prography.ui.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Created by MyeongKi.
 */
@Composable
fun SpaceHeight(height: Int) {
    Spacer(modifier = Modifier.height(height.dp))
}
@Composable
fun SpaceHeight(height: Float) {
    Spacer(modifier = Modifier.height(height.dp))
}
@Composable
fun SpaceWidth(width: Int) {
    Spacer(modifier = Modifier.width(width.dp))
}