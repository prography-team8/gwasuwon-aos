import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * Created by MyeongKi.
 */
sealed interface NavigationEvent {
    data object NavigateSignInRoute : NavigationEvent
    data object NavigateSignUpRoute : NavigationEvent
    data object NavigateLessonsRoute : NavigationEvent
    data object NavigateLessonInvitedRoute : NavigationEvent
    data object NavigateCreateLessonRoute : NavigationEvent
    data class NavigateLessonDeatailRoute(val lessonId: Long) : NavigationEvent
    data class NavigateSuccessCreateLessonRoute(val lessonId: Long) : NavigationEvent
    data class NavigateLessonInfoDetailRoute(val lessonId: Long) : NavigationEvent
    data class NavigateInviteStudentQrRoute(val lessonId: Long) : NavigationEvent
    data class NavigateLessonCertificationQrRoute(val lessonId: Long) : NavigationEvent
    data class NavigateLessonContractQrRoute(val lessonId: Long) : NavigationEvent
    data class NavigateWeb(val url: String) : NavigationEvent
    data object PopBack : NavigationEvent
}

fun MutableSharedFlow<NavigationEvent>.subscribeNavigationEvent(
    navActions: NavigationActions,
    coroutineScope: CoroutineScope
): Job {
    return this
        .onEach {
            when (it) {
                is NavigationEvent.NavigateSignInRoute -> {
                    navActions.navigateSignInRoute()
                }

                is NavigationEvent.NavigateSignUpRoute -> {
                    navActions.navigateSignUpRoute()

                }

                is NavigationEvent.NavigateLessonsRoute -> {
                    navActions.navigateLessonsRoute()
                }

                is NavigationEvent.NavigateLessonInvitedRoute -> {
                    navActions.navigateLessonInvitedRoute()
                }

                is NavigationEvent.NavigateCreateLessonRoute -> {
                    navActions.navigateCreateLessonRoute()

                }

                is NavigationEvent.NavigateLessonDeatailRoute -> {
                    navActions.navigateLessonDetailRoute(it.lessonId)
                }

                is NavigationEvent.NavigateSuccessCreateLessonRoute -> {
                    navActions.navigateSuccessCreateLessonRoute(it.lessonId)
                }

                is NavigationEvent.NavigateLessonInfoDetailRoute -> {
                    navActions.navigateLessonInfoDetailRoute(it.lessonId)
                }

                is NavigationEvent.NavigateInviteStudentQrRoute -> {
                    navActions.navigateInviteStudentQrRoute(it.lessonId)
                }

                is NavigationEvent.NavigateLessonContractQrRoute -> {
                    navActions.navigateLessonContractQrRoute(it.lessonId)
                }

                is NavigationEvent.NavigateLessonCertificationQrRoute -> {
                    navActions.navigateLessonCertificationQrRoute(it.lessonId)
                }

                is NavigationEvent.NavigateWeb -> {
                    navActions.navigateWeb(it.url)
                }

                is NavigationEvent.PopBack -> {
                    navActions.popBackStack()
                }
            }
        }
        .launchIn(coroutineScope)
}