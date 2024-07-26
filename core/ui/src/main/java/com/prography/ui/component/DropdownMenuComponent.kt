package com.prography.ui.component

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.prography.ui.GwasuwonTypography
import com.prography.ui.R
import com.prography.ui.configuration.toColor
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

/**
 * Created by MyeongKi.
 */
@Composable
fun DropdownMenuComponent(
    @StringRes defaultOptionTextResId: Int,
    @StringRes selectedOptionTextResId: Int?,
    optionResIds: ImmutableList<Int>,
    available: Boolean = true,
    onOptionSelected: (Int) -> Unit,
) {
    DropdownMenuComponent(
        defaultOptionText = stringResource(id = defaultOptionTextResId),
        selectedOptionText = selectedOptionTextResId?.let { stringResource(id = it) },
        option = optionResIds.map { stringResource(id = it) }.toImmutableList(),
        available = available,
        onOptionSelected = onOptionSelected

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuComponent(
    defaultOptionText: String,
    selectedOptionText: String?,
    option: ImmutableList<String>,
    available: Boolean = true,
    onOptionSelected: (Int) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    var rowWidth by remember { mutableIntStateOf(0) }

    ExposedDropdownMenuBox(
        modifier = Modifier
            .onGloballyPositioned { coordinates ->
                rowWidth = coordinates.size.width
            },
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        val conner = dimensionResource(id = R.dimen.common_btn_conner)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor()
                .exposedDropdownSize(false)
                .border(
                    1.dp,
                    GwasuwonConfigurationManager.colors.lineRegularNormal.toColor(),
                    RoundedCornerShape(
                        topStart = conner,
                        topEnd = conner,
                        bottomStart = if (expanded) 0.dp else conner,
                        bottomEnd = if (expanded) 0.dp else conner
                    )
                )
                .padding(vertical = 12.dp, horizontal = 16.dp)
                .clickable {
                    if (available) {
                        expanded = true
                    }
                }

        ) {
            BasicTextField(
                modifier = Modifier
                    .weight(1f),
                value = selectedOptionText ?: defaultOptionText,
                onValueChange = {},
                textStyle = GwasuwonTypography.Body1NormalRegular.textStyle
                    .copy(
                        color = if (selectedOptionText == null) {
                            GwasuwonConfigurationManager.colors.labelAssistive.toColor()
                        } else {
                            GwasuwonConfigurationManager.colors.labelNormal.toColor()
                        }
                    ),
                singleLine = true,
                readOnly = true,
            )
            Image(
                painter = if (expanded.not()) {
                    painterResource(id = R.drawable.icon_dropdown)
                } else {
                    painterResource(id = R.drawable.icon_dropdown_up)
                },
                contentDescription = "dropdown icon",
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.common_icon_small_size)),
            )
        }

        DropdownMenu(
            modifier = Modifier
                .background(GwasuwonConfigurationManager.colors.backgroundRegularNormal.toColor())
                .width(
                    with(LocalDensity.current) {
                        rowWidth.toDp()
                    }
                ),
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            option.forEachIndexed { index, text ->
                Column(
                    modifier = Modifier.clickable {
                        expanded = false
                        onOptionSelected(index)
                    }
                ) {
                    if (option.size > 1) {
                        when (index) {
                            0 -> {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.common_large_padding)),
                                    text = text,
                                    style = GwasuwonTypography.Body1NormalRegular.textStyle
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                HorizontalDivider(
                                    modifier = Modifier.fillMaxWidth(),
                                    color = GwasuwonConfigurationManager.colors.lineRegularNormal.toColor(),
                                    thickness = 1.dp
                                )
                            }

                            option.size - 1 -> {
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.common_large_padding)),
                                    text = text,
                                    style = GwasuwonTypography.Body1NormalRegular.textStyle
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                            }

                            else -> {
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.common_large_padding)),
                                    text = text,
                                    style = GwasuwonTypography.Body1NormalRegular.textStyle
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                HorizontalDivider(
                                    modifier = Modifier.fillMaxWidth(),
                                    color = GwasuwonConfigurationManager.colors.lineRegularNormal.toColor(),
                                    thickness = 1.dp
                                )
                            }
                        }
                    } else {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.common_large_padding)),
                            text = text,
                            style = GwasuwonTypography.Body1NormalRegular.textStyle
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }
        }
    }
}
