package com.prography.ui.component

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.prography.ui.GwasuwonTypography
import com.prography.ui.R
import com.prography.ui.configuration.toColor

/**
 * Created by MyeongKi.
 */
@Composable
fun CommonToolbar(
    modifier: Modifier = Modifier,
    @StringRes titleRes: Int,
) {
    CommonToolbar(
        modifier = modifier,
        title = stringResource(id = titleRes)

    )
}

@Composable
fun CommonToolbar(
    modifier: Modifier = Modifier,
    title: String,
) {
    Text(
        modifier = modifier.padding(vertical = 8.dp),
        text = title,
        style = GwasuwonTypography.Heading1Bold.textStyle
    )
}

@Composable
fun CommonToolbar(
    modifier: Modifier = Modifier,
    title: String,
    onClickBack: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.icon_back),
            contentDescription = "back button",
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.common_icon_small_size))
                .clickable(onClick = onClickBack),
        )
        Spacer(modifier = Modifier.width(8.dp))
        CommonToolbar(
            modifier = Modifier.weight(1f),
            title = title
        )
    }
}

@Composable
fun CommonToolbar(
    modifier: Modifier = Modifier,
    @StringRes titleRes: Int,
    onClickBack: () -> Unit
) {
    CommonToolbar(
        modifier = modifier,
        title = stringResource(id = titleRes),
        onClickBack = onClickBack
    )
}


@Composable
fun CommonToolbar(
    modifier: Modifier = Modifier,
    @StringRes titleRes: Int,
    onClickBack: () -> Unit,
    onClickRight: () -> Unit,
    isVisibleRight: Boolean,
    @StringRes rightTextRes: Int
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CommonToolbar(
            modifier = modifier.weight(1f),
            titleRes = titleRes,
            onClickBack = onClickBack
        )
        if (isVisibleRight) {
            Text(
                text = stringResource(id = rightTextRes),
                style = GwasuwonTypography.Label2Bold.textStyle,
                color = GwasuwonConfigurationManager.colors.primaryNormal.toColor(),
                modifier = Modifier
                    .clickable(onClick = onClickRight)
                    .padding(end = 8.dp),
            )
        }
    }
}

@Composable
fun CommonToolbar(
    modifier: Modifier = Modifier,
    title: String,
    onClickBack: () -> Unit,
    isVisibleRight: Boolean,
    rightContent: @Composable () -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CommonToolbar(
            modifier = modifier.weight(1f),
            title = title,
            onClickBack = onClickBack
        )
        if (isVisibleRight) {
            rightContent()
        }
    }
}
