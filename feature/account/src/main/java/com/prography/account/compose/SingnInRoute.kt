package com.prography.account.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prography.account.SignInIntent
import com.prography.account.SignInViewModel
import com.prography.configuration.R
import com.prography.configuration.toColor
import com.prography.configuration.ui.GwasuwonConfigurationManager

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
}

@Composable
fun SignInScreen(
    intent: (SignInIntent) -> Unit
) {
    Box(modifier = Modifier.padding(dimensionResource(id = R.dimen.sign_in_horizontal_padding))) {
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
            color = GwasuwonConfigurationManager.colors.semanticPrimaryNormal.toColor(),
            fontWeight = FontWeight.Bold,
            fontSize = dimensionResource(id = R.dimen.login_title_size).value.sp,

            )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = stringResource(id = R.string.sign_in_desc),
            color = GwasuwonConfigurationManager.colors.semanticPrimaryNormal.toColor(),
            fontWeight = FontWeight.W400,
            fontSize = dimensionResource(id = R.dimen.login_desc_size).value.sp,
        )
    }
}

@Composable
private fun KakaoLoginBtn(
    modifier:Modifier,
    onClickBtn: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.login_btn_conner)))
            .background(GwasuwonConfigurationManager.colors.kakaoColor.toColor())
            .padding(dimensionResource(id = R.dimen.login_btn_padding))
            .clickable(onClick = onClickBtn)
    ) {
        Image(
            painter = painterResource(id = R.drawable.kakao_logo),
            contentDescription = "logo",
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(dimensionResource(id = R.dimen.login_icon_size)),
        )
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = stringResource(id = R.string.kakao_login),
            fontSize = dimensionResource(id = R.dimen.login_btn_text_size).value.sp,
            color = GwasuwonConfigurationManager.colors.semanticStaticBlack.toColor(),
            fontWeight = FontWeight.W500
        )
    }
}
