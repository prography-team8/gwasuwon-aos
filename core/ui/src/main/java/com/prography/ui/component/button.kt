package com.prography.ui.component

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.unit.dp
import com.prography.ui.GwasuwonTypography
import com.prography.ui.R
import com.prography.ui.configuration.toColor

/**
 * Created by MyeongKi.
 */
@Composable
fun CommonBorderButton(
    modifier: Modifier = Modifier.fillMaxWidth(),
    @StringRes textResId: Int,
    onClickNext: () -> Unit
) {
    Box(
        modifier = modifier
            .wrapContentHeight()
            .border(
                width = 1.dp,
                color = GwasuwonConfigurationManager.colors.primaryNormal.toColor(),
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.common_btn_conner))
            )
            .background(
                GwasuwonConfigurationManager.colors.backgroundRegularNormal.toColor()
            )
            .padding(dimensionResource(id = R.dimen.common_padding))
            .clickable(onClick = {
                onClickNext()
            })
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = stringResource(id = textResId),
            color = GwasuwonConfigurationManager.colors.primaryNormal.toColor(),
            style = GwasuwonTypography.Body1NormalBold.textStyle
        )
    }
}

@Composable
fun CommonButton(
    modifier: Modifier = Modifier,
    @StringRes textResId: Int,
    isAvailable: Boolean,
    onClickNext: () -> Unit
) {
    Box(
        modifier = modifier
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
            .padding(dimensionResource(id = R.dimen.common_padding))
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
