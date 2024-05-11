package com.prography.configuration

import androidx.annotation.ColorRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource

/**
 * Created by MyeongKi.
 */
@Composable
@ColorRes
fun Int.toColor() = colorResource(id = this)