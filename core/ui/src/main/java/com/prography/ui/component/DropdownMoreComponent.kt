package com.prography.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.prography.ui.GwasuwonTypography
import com.prography.ui.R
import com.prography.ui.configuration.toColor
import kotlinx.collections.immutable.ImmutableList

/**
 * Created by MyeongKi.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMoreComponent(
    optionRes: ImmutableList<Int>,
    onOptionSelected: (Int) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        Image(
            painter = painterResource(id = R.drawable.icon_more_dot),
            contentDescription = "lesson detail more",
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.common_icon_small_size))
                .clickable {
                    expanded = true
                },
        )

        DropdownMenu(
            modifier = Modifier
                .background(GwasuwonConfigurationManager.colors.backgroundRegularNormal.toColor()),
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            optionRes.forEachIndexed { index, textRes ->
                Column(
                    modifier = Modifier.clickable {
                        expanded = false
                        onOptionSelected(index)
                    },
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    if (optionRes.size > 1) {
                        when (index) {
                            0 -> {
                                Text(
                                    modifier = Modifier
                                        .padding(top = 0.dp, bottom = 8.dp, start = 8.dp, end = 8.dp),
                                    text = stringResource(id = textRes),
                                    style = GwasuwonTypography.Body1NormalRegular.textStyle
                                )
                                HorizontalDivider(
                                    modifier = Modifier.fillMaxWidth(),
                                    color = GwasuwonConfigurationManager.colors.lineRegularNormal.toColor(),
                                    thickness = 1.dp
                                )
                            }

                            optionRes.size - 1 -> {
                                Text(
                                    modifier = Modifier
                                        .padding(bottom = 0.dp, top = 8.dp, start = 8.dp, end = 8.dp),
                                    text = stringResource(id = textRes),
                                    style = GwasuwonTypography.Body1NormalRegular.textStyle
                                )
                            }

                            else -> {
                                Text(
                                    modifier = Modifier
                                        .padding(8.dp),
                                    text = stringResource(id = textRes),
                                    style = GwasuwonTypography.Body1NormalRegular.textStyle
                                )
                                HorizontalDivider(
                                    modifier = Modifier.fillMaxWidth(),
                                    color = GwasuwonConfigurationManager.colors.lineRegularNormal.toColor(),
                                    thickness = 1.dp
                                )
                            }
                        }
                    } else {
                        Text(
                            modifier = Modifier
                                .padding(horizontal = 8.dp),
                            text = stringResource(id = textRes),
                            style = GwasuwonTypography.Body1NormalRegular.textStyle
                        )
                    }
                }
            }
        }
    }
}
