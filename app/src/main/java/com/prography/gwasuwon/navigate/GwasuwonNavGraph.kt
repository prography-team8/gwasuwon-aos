package com.prography.gwasuwon.navigate

import GwasuwonPath
import NavigationActions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.prography.account.SignInViewModel
import com.prography.account.compose.SignInRoute
import com.prography.gwasuwon.AppContainer
import subscribeNavigationEvent

/**
 * Created by MyeongKi.
 */
@Composable
fun GwasuwonNavGraph(
    onEmptyBackStack: () -> Unit
) {
    val navController = rememberNavController()
    val navigationActions = remember(navController) {
        NavigationActions(navController, onEmptyBackStack)
    }
    LaunchedEffect(Unit) {
        AppContainer.navigateEventFlow.subscribeNavigationEvent(
            navActions = navigationActions,
            coroutineScope = this,
        )
    }
    //FIXME startDestination 수정 필요.
    NavHost(
        navController = navController,
        startDestination = GwasuwonPath.SingInPath.getDestination(),
        modifier = Modifier
    ) {
        with(GwasuwonPath.SingInPath) {
            composable(getDestination(), arguments) {
                SignInRoute(
                    viewModel = viewModel(
                        factory = SignInViewModel.provideFactory(
                            navigateFlow = AppContainer.navigateEventFlow,
                            signInUseCase = AppContainer.signInUseCase,
                            accountOuterFlow = AppContainer.socialLoginEventFlow
                        )
                    )
                )
            }
        }
        with(GwasuwonPath.SingUpPath) {
            composable(getDestination(), arguments) {
                Text(text = "hi im SingUpPath")

            }
        }
        with(GwasuwonPath.LessonPath) {
            composable(getDestination(), arguments) {
                Text(text = "hi im lesson")
            }
        }
    }
}