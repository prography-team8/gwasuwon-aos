package com.prography.lesson.compose.create

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.prography.configuration.R
import com.prography.configuration.toColor
import com.prography.configuration.ui.GwasuwonConfigurationManager
import com.prography.ui.GwasuwonTypography

/**
 * Created by MyeongKi.
 */
@Composable
internal fun LessonInfoInputItem(
    @StringRes titleRes: Int,
    @StringRes hintRes: Int,
    inputText: String,
    onValueChange: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = titleRes),
            color = GwasuwonConfigurationManager.colors.labelNormal.toColor(),
            style = GwasuwonTypography.Caption1Bold.textStyle
        )
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .then(
                    if (inputText.isNotEmpty()) {
                        Modifier.border(
                            width = 1.dp,
                            color = GwasuwonConfigurationManager.colors.lineRegularNormal.toColor(),
                            shape = RoundedCornerShape(dimensionResource(id = R.dimen.common_btn_conner))
                        )
                    } else {
                        Modifier.background(
                            GwasuwonConfigurationManager.colors.backgroundElevatedAlternative.toColor(),
                            RoundedCornerShape(dimensionResource(id = R.dimen.common_btn_conner))
                        )
                    }
                )
                .padding(vertical = 12.dp, horizontal = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = stringResource(id = hintRes),
                color = if (inputText.isEmpty()) GwasuwonConfigurationManager.colors.labelAssistive.toColor() else Color.Transparent,
                style = GwasuwonTypography.Body1NormalRegular.textStyle
            )
            BasicTextField(
                value = inputText,
                onValueChange = onValueChange,
                textStyle = GwasuwonTypography.Body1NormalRegular.textStyle.copy(
                    color = GwasuwonConfigurationManager.colors.labelNormal.toColor()
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
