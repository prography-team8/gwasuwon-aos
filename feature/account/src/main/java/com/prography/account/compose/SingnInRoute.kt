package com.prography.account.compose

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.prography.account.SignInIntent
import com.prography.account.SignInViewModel

/**
 * Created by MyeongKi.
 */

@Composable
fun SignInRoute(
    viewModel: SignInViewModel
) {
    val intentInvoker = viewModel.machine.intentInvoker
    SignInScreen(intentInvoker)
}

@Composable
fun SignInScreen(
    intent: (SignInIntent) -> Unit
) {
    Text(text = "test", color = Color.Green, fontSize = 30.sp, modifier = Modifier.clickable {
        intent(SignInIntent.ClickKakaoSignIn)
    })
}
