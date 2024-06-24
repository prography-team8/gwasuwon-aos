package com.prography.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.prography.configuration.R
import com.prography.configuration.toColor
import com.prography.configuration.ui.GwasuwonConfigurationManager

/**
 * Created by MyeongKi.
 */

@Composable
fun CommonButton(
    @StringRes textResId: Int,
    isAvailable: Boolean,
    onClickNext: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.common_btn_conner)))
            .background(
                if (isAvailable) {
                    GwasuwonConfigurationManager.colors.primaryNormal.toColor()
                } else {
                    GwasuwonConfigurationManager.colors.interactionInactive.toColor()
                }
            )
            .padding(dimensionResource(id = R.dimen.common_btn_padding))
            .clickable(onClick = {
                if (isAvailable) {
                    onClickNext()
                }
            })
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = stringResource(id = textResId),
            color = if (isAvailable) {
                GwasuwonConfigurationManager.colors.staticWhite.toColor()
            } else {
                GwasuwonConfigurationManager.colors.labelDisable.toColor()
            },
            style = GwasuwonTypography.Body1NormalBold.textStyle
        )
    }
}
