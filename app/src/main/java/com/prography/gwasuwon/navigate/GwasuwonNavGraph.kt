package com.prography.gwasuwon.navigate

import GwasuwonPath
import NavigationActions
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
import com.prography.domain.account.model.AccountRole
import com.prography.domain.account.model.AccountStatus
import com.prography.gwasuwon.AppContainer
import com.prography.lesson.CreateLessonViewModel
import com.prography.lesson.LessonDetailViewModel
import com.prography.lesson.LessonInfoDetailViewModel
import com.prography.lesson.LessonsViewModel
import com.prography.lesson.SuccessCreateLessonViewModel
import com.prography.lesson.compose.LessonDetailRoute
import com.prography.lesson.compose.LessonInfoDetailRoute
import com.prography.lesson.compose.LessonsRoute
import com.prography.lesson.compose.SuccessCreateLessonRoute
import com.prography.lesson.compose.create.CreateLessonRoute
import com.prography.qr.InviteStudentQrViewModel
import com.prography.qr.LessonCertificationQrViewModel
import com.prography.qr.LessonContractQrViewModel
import com.prography.qr.LessonInvitedViewModel
import com.prography.qr.compose.InviteStudentQrRoute
import com.prography.qr.compose.LessonCertificationQrRoute
import com.prography.qr.compose.LessonContractQrRoute
import com.prography.qr.compose.LessonInvitedRoute
import subscribeNavigationEvent

/**
 * Created by MyeongKi.
 */
@Composable
fun GwasuwonNavGraph(
    accountInfoManager: AccountInfoManager,
    navigateWeb: (String) -> Unit,
    onEmptyBackStack: () -> Unit,
) {
    val navController = rememberNavController()
    val navigationActions = remember(navController) {
        NavigationActions(
            navController,
            navigateWeb,
            onEmptyBackStack
        )
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
                if (accountInfoManager.getAccountInfo()?.role == AccountRole.STUDENT) {
                    val invitedLessonId = AppContainer.accountPreference.getInvitedLessonId()
                    if (invitedLessonId == null) {
                        GwasuwonPath.LessonInvitedPath.getDestination()
                    } else {
                        GwasuwonPath.LessonDetailPath(invitedLessonId).getDestination()
                    }
                } else {
                    GwasuwonPath.LessonsPath.getDestination()
                }
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
        with(GwasuwonPath.LessonInvitedPath) {
            composable(getDestination(), arguments) {
                LessonInvitedRoute(
                    viewModel = viewModel(
                        factory = LessonInvitedViewModel.provideFactory(
                            navigateFlow = AppContainer.navigateEventFlow,
                            commonQrFlow = AppContainer.qrEventFlow,
                            participateLessonUseCase = AppContainer.participateLessonUseCase,
                            accountPreference = AppContainer.accountPreference
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
                LessonInfoDetailRoute(
                    viewModel = viewModel(
                        factory = LessonInfoDetailViewModel.provideFactory(
                            lessonId = lessonId,
                            navigateFlow = AppContainer.navigateEventFlow,
                            commonLessonEvent = AppContainer.commonLessonEvent,
                            loadLessonUseCase = AppContainer.loadLessonUseCase,
                            updateLessonUseCase = AppContainer.updateLessonUseCase
                        )
                    )
                )
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
        with(GwasuwonPath.LessonCertificationQrPath()) {
            composable(getDestination(), arguments) {
                val lessonId = it.arguments?.getLong(GwasuwonPath.LessonCertificationQrPath.ArgumentName.LESSON_ID.name) ?: 0L
                LessonCertificationQrRoute(
                    viewModel = viewModel(
                        factory = LessonCertificationQrViewModel.provideFactory(
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
                LessonContractQrRoute(
                    viewModel = viewModel(
                        factory = LessonContractQrViewModel.provideFactory(
                            lessonId = lessonId,
                            navigateFlow = AppContainer.navigateEventFlow,
                            loadContractUrlUseCase = AppContainer.loadLessonContractUrlUseCase,
                            clipboardHelper = AppContainer.clipboardHelper,
                        )
                    )
                )
            }
        }

        with(GwasuwonPath.LessonDetailPath()) {
            composable(getDestination(), arguments) {
                val lessonId = it.arguments?.getLong(GwasuwonPath.LessonDetailPath.ArgumentName.LESSON_ID.name) ?: 0L
                LessonDetailRoute(
                    viewModel = viewModel(
                        factory = LessonDetailViewModel.provideFactory(
                            lessonId = lessonId,
                            isTeacher = accountInfoManager.getAccountInfo()?.role == AccountRole.TEACHER,
                            commonQrFlow = AppContainer.qrEventFlow,
                            navigateFlow = AppContainer.navigateEventFlow,
                            commonLessonEvent = AppContainer.commonLessonEvent,
                            loadLessonUseCase = AppContainer.loadLessonUseCase,
                            loadLessonDatesUseCase = AppContainer.loadLessonDatesUseCase,
                            deleteLessonUseCase = AppContainer.deleteLessonUseCase,
                            checkLessonByAttendanceUseCase = AppContainer.checkLessonByAttendanceUseCase,
                            isShowingNotifyLessonDeductedDialogUseCase = AppContainer.isShowingNotifyLessonDeductedDialogUseCase,
                            updateShownNotifyLessonDeductedDialogUseCase = AppContainer.updateShownNotifyLessonDeductedDialogUseCase,
                            certificateLessonUseCase = AppContainer.certificateLessonUseCase
                        )
                    ),
                    isTeacher = accountInfoManager.getAccountInfo()?.role == AccountRole.TEACHER
                )
            }
        }
    }
}