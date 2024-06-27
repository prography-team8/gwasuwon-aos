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
import com.prography.account.SignUpViewModel
import com.prography.account.compose.SignInRoute
import com.prography.account.compose.SignUpRoute
import com.prography.domain.account.AccountInfoManager
import com.prography.domain.account.model.AccountStatus
import com.prography.gwasuwon.AppContainer
import com.prography.lesson.CreateLessonViewModel
import com.prography.lesson.LessonsViewModel
import com.prography.lesson.SuccessCreateLessonViewModel
import com.prography.lesson.compose.create.CreateLessonRoute
import com.prography.lesson.compose.LessonsRoute
import com.prography.lesson.compose.SuccessCreateLessonRoute
import com.prography.qr.InviteStudentQrViewModel
import com.prography.qr.compose.InviteStudentQrRoute
import subscribeNavigationEvent

/**
 * Created by MyeongKi.
 */
@Composable
fun GwasuwonNavGraph(
    accountInfoManager: AccountInfoManager,
    onEmptyBackStack: () -> Unit,
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
        startDestination = when (accountInfoManager.getAccountInfo()?.status) {
            AccountStatus.ACTIVE -> {
                //TODO 캐싱된 active 상태로 lesson 페이지로 랜딩을 유도하고, 해당 페이지에서 refresh token까지 로그인을 실패한 경우에 다시 로그인 페이지로 랜딩하게 수정이 필요.
                GwasuwonPath.LessonsPath.getDestination()
            }

            else -> {
                GwasuwonPath.SingInPath.getDestination()
            }
        },
        modifier = Modifier
    ) {
        with(GwasuwonPath.SingInPath) {
            composable(getDestination(), arguments) {
                SignInRoute(
                    viewModel = viewModel(
                        factory = SignInViewModel.provideFactory(
                            navigateFlow = AppContainer.navigateEventFlow,
                            signInUseCase = AppContainer.signInUseCase,
                            socialLoginFlow = AppContainer.socialLoginEventFlow
                        )
                    )
                )
            }
        }
        with(GwasuwonPath.SingUpPath) {
            composable(getDestination(), arguments) {
                SignUpRoute(
                    viewModel = viewModel(
                        factory = SignUpViewModel.provideFactory(
                            navigateFlow = AppContainer.navigateEventFlow,
                            signUpUseCase = AppContainer.signUpUseCase,
                        )
                    )
                )
            }
        }
        with(GwasuwonPath.LessonsPath) {
            composable(getDestination(), arguments) {
                LessonsRoute(
                    viewModel = viewModel(
                        factory = LessonsViewModel.provideFactory(
                            navigateFlow = AppContainer.navigateEventFlow,
                            loadLessonsUseCase = AppContainer.loadLessonsUseCase,
                            commonLessonEvent = AppContainer.commonLessonEvent
                        )
                    )
                )
            }
        }
        with(GwasuwonPath.CrateLessonPath) {
            composable(getDestination(), arguments) {
                CreateLessonRoute(
                    viewModel = viewModel(
                        factory = CreateLessonViewModel.provideFactory(
                            navigateFlow = AppContainer.navigateEventFlow,
                            createLessonUseCase = AppContainer.createLessonUseCase,
                            commonLessonEvent = AppContainer.commonLessonEvent
                        )
                    )
                )
            }
        }
        with(GwasuwonPath.SuccessCreateLessonPath()) {
            composable(getDestination(), arguments) {
                val lessonId = it.arguments?.getLong(GwasuwonPath.SuccessCreateLessonPath.ArgumentName.LESSON_ID.name) ?: 0L
                SuccessCreateLessonRoute(
                    viewModel = viewModel(
                        factory = SuccessCreateLessonViewModel.provideFactory(
                            lessonId = lessonId,
                            navigateFlow = AppContainer.navigateEventFlow,
                        )
                    )
                )
            }
        }

        with(GwasuwonPath.LessonInfoDetailPath()) {
            composable(getDestination(), arguments) {
                val lessonId = it.arguments?.getLong(GwasuwonPath.LessonInfoDetailPath.ArgumentName.LESSON_ID.name) ?: 0L
                Text(text = "hi im lesson info detail")
            }
        }

        with(GwasuwonPath.InviteStudentQrPath()) {
            composable(getDestination(), arguments) {
                val lessonId = it.arguments?.getLong(GwasuwonPath.InviteStudentQrPath.ArgumentName.LESSON_ID.name) ?: 0L
                InviteStudentQrRoute(
                    viewModel = viewModel(
                        factory = InviteStudentQrViewModel.provideFactory(
                            lessonId = lessonId,
                            navigateFlow = AppContainer.navigateEventFlow,
                        )
                    )
                )
            }
        }

        with(GwasuwonPath.LessonContractQrPath()) {
            composable(getDestination(), arguments) {
                val lessonId = it.arguments?.getLong(GwasuwonPath.LessonContractQrPath.ArgumentName.LESSON_ID.name) ?: 0L
                Text(text = "hi im LessonContractQrPath")
            }
        }

        with(GwasuwonPath.LessonDetailPath()) {
            composable(getDestination(), arguments) {
                val lessonId = it.arguments?.getLong(GwasuwonPath.LessonDetailPath.ArgumentName.LESSON_ID.name) ?: ""
                Text(text = "hi im lesson detail")
            }
        }
    }
}