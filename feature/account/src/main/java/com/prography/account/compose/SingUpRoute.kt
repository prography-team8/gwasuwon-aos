package com.prography.account.compose

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.prography.account.SignUpIntent
import com.prography.account.SignUpUiState
import com.prography.account.SignUpViewModel
import com.prography.ui.GwasuwonTypography
import com.prography.ui.R
import com.prography.ui.component.CommonButton
import com.prography.ui.component.GwasuwonConfigurationManager
import com.prography.ui.component.SpaceHeight
import com.prography.ui.component.SpaceWidth
import com.prography.ui.configuration.toColor

/**
 * Created by MyeongKi.
 */
@Composable
fun SignUpRoute(
    viewModel: SignUpViewModel
) {
    val uiState = viewModel.machine.uiState.collectAsState().value
    val intentInvoker = viewModel.machine.intentInvoker
    when (uiState) {
        is SignUpUiState.Agreement -> SignUpAgreementScreen(uiState, intentInvoker)
        is SignUpUiState.SelectRole -> SignUpSelectRoleScreen(intentInvoker)

    }
}

@Composable
private fun SignUpAgreementScreen(
    uiState: SignUpUiState.Agreement,
    intent: (SignUpIntent) -> Unit
) {
    Column(
        modifier = Modifier.padding(
            horizontal = dimensionResource(id = R.dimen.common_large_padding)
        )
    ) {
        SignUpAgreementBody(
            modifier = Modifier.weight(1f)
        )
        SignUpAgreementBottom(
            uiState = uiState,
            intent = intent
        )
    }
}

@Composable
private fun SignUpAgreementBody(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(
                horizontal = dimensionResource(id = R.dimen.common_large_padding)
            )
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.sign_up_agreement_title),
            color = GwasuwonConfigurationManager.colors.labelNormal.toColor(),
            style = GwasuwonTypography.Display2Bold.textStyle

        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.sign_up_agreement_desc),
            color = GwasuwonConfigurationManager.colors.labelNormal.toColor(),
            style = GwasuwonTypography.Label1NormalRegular.textStyle
        )
    }
}

@Composable
private fun SignUpAgreementBottom(
    modifier: Modifier = Modifier,
    uiState: SignUpUiState.Agreement,
    intent: (SignUpIntent) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        AllAgreementButton(
            isAllAgreement = uiState.isAllAgreement,
        ) {
            intent(SignUpIntent.ClickAllAgreement)
        }
        AgreementButton(
            textResId = R.string.personal_information_agreement,
            isAgreement = uiState.isPersonalInformationAgreement,
            onCheck = {
                intent(SignUpIntent.CheckPersonalInformationAgreement)
            },
            onClickArrow = {
                intent(SignUpIntent.ClickArrowPersonalInformationAgreement)
            }
        )
        AgreementButton(
            textResId = R.string.gwasuwon_agreement,
            isAgreement = uiState.isGwasuwonServiceAgreement,
            onCheck = {
                intent(SignUpIntent.CheckGwasuwonServiceAgreement)
            },
            onClickArrow = {
                intent(SignUpIntent.ClickArrowGwasuwonServiceAgreement)
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        CommonButton(
            textResId = R.string.next,
            isAvailable = uiState.isAvailableNextButton,
            onClickNext = {
                intent(SignUpIntent.ClickNextButton)
            }
        )
    }
}

@Composable
private fun AllAgreementButton(
    isAllAgreement: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(
                RoundedCornerShape(dimensionResource(id = R.dimen.common_btn_large_conner))
            )
            .background(
                GwasuwonConfigurationManager.colors.backgroundElevatedAlternative.toColor()
            )
            .padding(dimensionResource(id = R.dimen.common_large_padding))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                colorFilter = ColorFilter.tint(
                    if (isAllAgreement) {
                        GwasuwonConfigurationManager.colors.primaryNormal.toColor()
                    } else {
                        colorResource(id = R.color.neutral_95)
                    }
                ),
                painter = painterResource(id = R.drawable.material_symbols_check),
                contentDescription = "check",
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.common_icon_small_size))
                    .clickable(onClick = onClick),
            )
            SpaceWidth(width = 12)
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                text = stringResource(id = R.string.all_agreement),
                color = GwasuwonConfigurationManager.colors.labelNormal.toColor(),
                style = GwasuwonTypography.Body1NormalBold.textStyle
            )
        }

    }
}

@Composable
private fun AgreementButton(
    @StringRes textResId: Int,
    isAgreement: Boolean,
    onCheck: () -> Unit,
    onClickArrow: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(dimensionResource(id = R.dimen.common_large_padding))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                colorFilter = ColorFilter.tint(
                    if (isAgreement) {
                        GwasuwonConfigurationManager.colors.primaryNormal.toColor()
                    } else {
                        colorResource(id = R.color.neutral_95)
                    }
                ),
                painter = painterResource(id = R.drawable.material_symbols_check),
                contentDescription = "check",
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.common_icon_small_size))
                    .clickable(onClick = onCheck),
            )
            SpaceWidth(width = 12)
            Text(
                modifier = Modifier
                    .weight(1f),
                text = stringResource(id = textResId),
                color = GwasuwonConfigurationManager.colors.labelNormal.toColor(),
                style = GwasuwonTypography.Body1NormalBold.textStyle
            )
            Image(
                painter = painterResource(id = R.drawable.icon_more),
                contentDescription = "navigate",
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.common_icon_small_size))
                    .clickable(onClick = onClickArrow),
            )
        }

    }
}


@Composable
private fun SignUpSelectRoleScreen(
    intent: (SignUpIntent) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(
                horizontal = dimensionResource(id = R.dimen.common_large_padding)
            )
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.sign_up_select_role_title),
            color = GwasuwonConfigurationManager.colors.labelNormal.toColor(),
            style = GwasuwonTypography.Title2Bold.textStyle,
            textAlign = TextAlign.Center

        )
        SpaceHeight(height = 72)
        SignUpSelectRoleButton(
            textResId = R.string.sign_up_select_teacher,
            iconResId = R.drawable.icon_teacher,
        ) {
            intent(SignUpIntent.ClickTeacher)
        }
        SpaceHeight(height = 8)
        SignUpSelectRoleButton(
            textResId = R.string.sign_up_select_student,
            iconResId = R.drawable.icon_student,
        ) {
            intent(SignUpIntent.ClickStudent)
        }
    }
}

@Composable
private fun SignUpSelectRoleButton(
    @StringRes textResId: Int,
    @DrawableRes iconResId: Int,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(
                RoundedCornerShape(dimensionResource(id = R.dimen.common_btn_conner))
            )
            .background(
                GwasuwonConfigurationManager.colors.backgroundElevatedAlternative.toColor()
            )
            .padding(dimensionResource(id = R.dimen.common_medium_padding))
            .clickable(onClick = onClick)
    ) {
        Image(
            painter = painterResource(id = iconResId),
            contentDescription = "role icon",
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.common_icon_large_size))
                .align(Alignment.CenterStart),
        )
        Text(
            modifier = Modifier
                .wrapContentHeight()
                .align(Alignment.Center),
            text = stringResource(id = textResId),
            color = GwasuwonConfigurationManager.colors.staticBlack.toColor(),
            style = GwasuwonTypography.Headline1Bold.textStyle
        )

    }
}

