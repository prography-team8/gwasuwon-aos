package com.prography.gwasuwon.navigate

import GwasuwonPath
import NavigationActions
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.prography.configuration.toColor
import com.prography.configuration.ui.GwasuwonConfigurationManager
import com.prography.gwasuwon.AppContainer
import com.prography.usm_sample.SampleCountViewModel
import com.prography.usm_sample.compose.SampleCountRoute
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
    NavHost(
        navController = navController,
        startDestination = GwasuwonPath.Sample1Path.getDestination(),
        modifier = Modifier
    ) {
        with(GwasuwonPath.Sample1Path) {
            composable(getDestination(), arguments) {
                SampleCountRoute(
                    viewModel = viewModel(
                        factory = SampleCountViewModel.provideFactory(
                            navigateFlow = AppContainer.navigateEventFlow,
                            saveCurrentCountUseCase = AppContainer.sampleCountUseCase,
                            loadLastCountUseCase = AppContainer.sampleLoadUseCase
                        )
                    )
                )
            }
        }
        with(GwasuwonPath.Sample2Path(0)) {
            composable(getDestination(), arguments) {
                val currentCount: Int? = it.arguments?.getInt(GwasuwonPath.Sample2Path.ArgumentName.CURRENT_COUNT.name)
                Box {
                    Text(
                        fontSize = 40.sp,
                        text = currentCount.toString(),
                        color = GwasuwonConfigurationManager.colors.textColor.toColor())
                }
            }
        }
    }
}