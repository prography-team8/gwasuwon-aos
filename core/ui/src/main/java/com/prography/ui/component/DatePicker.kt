package com.prography.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.prography.ui.GwasuwonTypography
import com.prography.ui.R
import com.prography.ui.configuration.toColor
import com.prography.utils.date.DateUtils
import com.prography.utils.date.toDisplayYMDText
import com.prography.utils.date.toKrMonthDateTime
import com.prography.utils.date.toKtsTimeMillis
import com.prography.utils.date.toLocalDateTime

/**
 * Created by MyeongKi.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerButton(
    onClickConfirm: (dateMillisSelected: Long) -> Unit,
    selectedDate: Long = -1L,
    available: Boolean = true,
    onExpandPicker: () -> Unit
) {
    val initialSelectedDateMillis = remember {
        DateUtils.getCurrentDateTime()
    }
    var expanded by remember { mutableStateOf(false) }
    val currentDateText = remember {
        DateUtils.getCurrentLocalDateTime().toDisplayYMDText()
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                1.dp,
                GwasuwonConfigurationManager.colors.lineRegularNormal.toColor(),
                RoundedCornerShape(dimensionResource(id = R.dimen.common_btn_conner))
            )
            .padding(vertical = 12.dp, horizontal = 16.dp)
            .clickable {
                if (available) {
                    onExpandPicker()
                    expanded = true
                }
            }

    ) {
        Text(
            modifier = Modifier
                .weight(1f),
            text = if (selectedDate == -1L) {
                currentDateText
            } else {
                selectedDate.toLocalDateTime().toDisplayYMDText()
            },
            style = GwasuwonTypography.Body1NormalRegular.textStyle
                .copy(
                    color = if (selectedDate == -1L) {
                        GwasuwonConfigurationManager.colors.labelAssistive.toColor()
                    } else {
                        GwasuwonConfigurationManager.colors.labelNormal.toColor()
                    }
                ),
        )
        Image(
            painter = painterResource(id = R.drawable.icon_dropdown),
            contentDescription = "date pick icon",
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.common_icon_small_size)),
        )
    }
    if (expanded) {
        DatePickerDialog(
            onDismissRequest = { expanded = false },
            confirmButton = {},
            colors = DatePickerDefaults.colors(
                containerColor = GwasuwonConfigurationManager.colors.backgroundRegularNormal.toColor(),
            ),
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.common_btn_conner)),
        ) {
            val datePickerState = rememberDatePickerState(
                yearRange = 2024..2099,
                initialDisplayMode = DisplayMode.Picker,
                initialSelectedDateMillis = initialSelectedDateMillis,
                selectableDates = object : SelectableDates {
                    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                        return utcTimeMillis.toKtsTimeMillis().toKrMonthDateTime() >= DateUtils.getCurrentDateTime().toKrMonthDateTime()
                    }
                })

            DatePicker(
                state = datePickerState,
                colors = DatePickerDefaults.colors(
                    disabledSelectedDayContainerColor = GwasuwonConfigurationManager.colors.primaryNormal.toColor().copy(alpha = 0.5f),
                    containerColor = GwasuwonConfigurationManager.colors.backgroundRegularNormal.toColor(),
                    selectedDayContentColor = GwasuwonConfigurationManager.colors.staticWhite.toColor(),
                    selectedDayContainerColor = GwasuwonConfigurationManager.colors.primaryNormal.toColor(),
                    todayDateBorderColor = GwasuwonConfigurationManager.colors.primaryNormal.toColor(),
                    todayContentColor = GwasuwonConfigurationManager.colors.primaryNormal.toColor(),
                )
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                CommonBorderButton(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .wrapContentSize(),
                    textResId = R.string.common_confirm
                ) {
                    val dateClicked = datePickerState.selectedDateMillis?.toKtsTimeMillis()?.toKrMonthDateTime() ?: -1
                    if (dateClicked >= DateUtils.getCurrentDateTime().toKtsTimeMillis().toKrMonthDateTime()) {
                        onClickConfirm(dateClicked)
                    }
                    expanded = false
                }
            }
        }
    }
}