package com.prography.lesson.compose

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.prography.ui.R
import com.prography.ui.configuration.toColor
import com.prography.ui.component.GwasuwonConfigurationManager
import com.prography.lesson.SuccessCreateLessonIntent
import com.prography.lesson.SuccessCreateLessonViewModel
import com.prography.ui.component.CommonBorderButton
import com.prography.ui.component.CommonButton
import com.prography.ui.GwasuwonTypography
import com.prography.ui.component.SpaceHeight

/**
 * Created by MyeongKi.
 */
@Composable
fun SuccessCreateLessonRoute(
    viewModel: SuccessCreateLessonViewModel
) {
    SuccessCreateLessonScreen(
        intent = viewModel.machine.intentInvoker
    )
}

@Composable
private fun SuccessCreateLessonScreen(
    intent: (SuccessCreateLessonIntent) -> Unit
) {
    Column(
        modifier = Modifier.padding(
            horizontal = dimensionResource(id = R.dimen.common_large_padding)
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SpaceHeight(height = 40)
        Image(
            painter = painterResource(id = R.drawable.logo_large),
            contentDescription = "logo",
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.success_create_lesson_logo_size)),
        )
        SpaceHeight(height = 22)
        SuccessCreateLessonTitle()
        SpaceHeight(height = 8)
        SuccessCreateLessonSubtitle {
            intent(SuccessCreateLessonIntent.ClickLessonInfoDetail)
        }
        SpaceHeight(height = 24)
        DescriptionBox(
            title = "1. ",
            descriptionResId = R.string.success_create_lesson_desc_1
        )
        SpaceHeight(height = 11)
        DescriptionBox(
            title = "2. ",
            descriptionResId = R.string.success_create_lesson_desc_2
        )
        SpaceHeight(height = 11)
        DescriptionBox(
            title = "3. ",
            descriptionResId = R.string.success_create_lesson_desc_3
        )
        Spacer(modifier = Modifier.weight(1f))
        CommonButton(textResId = R.string.invite_student_qr, isAvailable = true) {
            intent(SuccessCreateLessonIntent.ClickInviteStudent)
        }
        SpaceHeight(height = 8)
        CommonButton(textResId = R.string.lesson_contract, isAvailable = true) {
            intent(SuccessCreateLessonIntent.ClickLessonContract)
        }
        SpaceHeight(height = 8)
        CommonBorderButton(textResId = R.string.navigate_home) {
            intent(SuccessCreateLessonIntent.ClickHome)
        }
    }
}

@Composable
private fun SuccessCreateLessonTitle() {
    Text(
        text = stringResource(id = R.string.success_create_lesson_title),
        style = GwasuwonTypography.Headline1Bold.textStyle,
        color = GwasuwonConfigurationManager.colors.labelNormal.toColor()
    )
}

@Composable
private fun SuccessCreateLessonSubtitle(
    onClick: () -> Unit
) {
    Text(
        modifier = Modifier.clickable(onClick = onClick),
        text = stringResource(id = R.string.success_create_lesson_subtitle),
        style = GwasuwonTypography.Headline1Bold.textStyle.copy(
            textDecoration = TextDecoration.Underline
        ),
        color = GwasuwonConfigurationManager.colors.primaryNormal.toColor()
    )
}

@Composable
private fun DescriptionBox(
    title: String,
    @StringRes descriptionResId: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.common_btn_conner)))
            .background(
                GwasuwonConfigurationManager.colors.backgroundElevatedAlternative.toColor()
            )
            .padding(dimensionResource(id = R.dimen.common_padding)),
        verticalAlignment = Alignment.Top

    ) {
        Text(
            modifier = Modifier.padding(top = 2.28.dp),
            text = title,
            color = GwasuwonConfigurationManager.colors.labelNeutral.toColor(),
            style = GwasuwonTypography.Label2Regular.textStyle

        )
        Text(
            text = stringResource(id = descriptionResId),
            color = GwasuwonConfigurationManager.colors.labelNeutral.toColor(),
            style = GwasuwonTypography.Label2Regular.textStyle
        )
    }
}