package com.prography.account.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.prography.account.SignInDialog
import com.prography.account.SignInIntent
import com.prography.account.SignInViewModel
import com.prography.ui.GwasuwonTypography
import com.prography.ui.R
import com.prography.ui.component.CommonDialogRoute
import com.prography.ui.component.GwasuwonConfigurationManager
import com.prography.ui.component.LoadingTransparentScreen
import com.prography.ui.configuration.toColor

/**
 * Created by MyeongKi.
 */

@Composable
fun SignInRoute(
    viewModel: SignInViewModel
) {
    val uiState = viewModel.machine.uiState.collectAsState().value
    val intentInvoker = viewModel.machine.intentInvoker
    SignInScreen(intentInvoker)
    if (uiState.dialog is SignInDialog.SignInCommonDialog) {
        CommonDialogRoute(dialog = uiState.dialog.state) {
            intentInvoker(SignInIntent.SignInCommonDialogIntent(it))
        }
    }
    if (uiState.isLoading) {
        LoadingTransparentScreen()
    }
}

@Composable
private fun SignInScreen(
    intent: (SignInIntent) -> Unit
) {
    Box(
        modifier = Modifier.padding(
            horizontal = dimensionResource(id = R.dimen.common_large_padding)
        )
    ) {
        SignInLogo()
        KakaoLoginBtn(modifier = Modifier.align(Alignment.BottomCenter)) {
            intent(SignInIntent.ClickKakaoSignIn)
        }
    }
}

@Composable
private fun SignInLogo(
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_large),
            contentDescription = "logo",
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.login_logo_size)),
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = stringResource(id = R.string.sign_in_title),
            color = GwasuwonConfigurationManager.colors.primaryNormal.toColor(),
            style = GwasuwonTypography.BrandMedium.textStyle

        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = stringResource(id = R.string.sign_in_desc),
            color = GwasuwonConfigurationManager.colors.primaryNormal.toColor(),
            style = GwasuwonTypography.Body1NormalMedium.textStyle
        )
    }
}

@Composable
private fun KakaoLoginBtn(
    modifier: Modifier,
    onClickBtn: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.common_btn_conner)))
            .background(GwasuwonConfigurationManager.colors.socialKakao.toColor())
            .padding(dimensionResource(id = R.dimen.common_large_padding))
            .clickable(onClick = onClickBtn)
    ) {
        Image(
            painter = painterResource(id = R.drawable.kakao_logo),
            contentDescription = "logo",
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(dimensionResource(id = R.dimen.common_icon_small_size)),
        )
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = stringResource(id = R.string.kakao_login),
            color = GwasuwonConfigurationManager.colors.staticBlack.toColor(),
            style = GwasuwonTypography.Body1NormalBold.textStyle
        )
    }
}
