package com.prography.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.prography.configuration.R

/**
 * Created by MyeongKi.
 */
@Composable
fun CommonToolbar(
    modifier: Modifier,
    @StringRes titleRes: Int,
) {
    Text(
        modifier = modifier,
        text = stringResource(id = titleRes),
        style = GwasuwonTypography.Heading1Bold.textStyle
        )
}

@Composable
fun CommonToolbar(
    modifier: Modifier,
    @StringRes titleRes: Int,
    onClickBack: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        Image(
            painter = painterResource(id = R.drawable.icon_back),
            contentDescription = "back button",
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.default_btn_icon_size))
                .clickable(onClick = onClickBack),
        )
        Spacer(modifier = Modifier.width(8.dp))
        CommonToolbar(
            modifier = Modifier.weight(1f),
            titleRes = titleRes
        )
    }
}
