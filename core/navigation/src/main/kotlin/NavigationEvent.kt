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
    data object NavigateCreateLessonRoute : NavigationEvent
    data class NavigateLessonDetailRoute(val lessonId: Long) : NavigationEvent
    data class NavigateSuccessCreateLessonRoute(val lessonId: Long) : NavigationEvent
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
                is NavigationEvent.NavigateCreateLessonRoute -> {
                    navActions.navigateCreateLessonRoute()

                }
                is NavigationEvent.NavigateLessonDetailRoute -> {
                    navActions.navigateLessonDetailRoute(it.lessonId)
                }
                is NavigationEvent.NavigateSuccessCreateLessonRoute -> {
                    navActions.navigateSuccessCreateLessonRoute(it.lessonId)
                }
                is NavigationEvent.PopBack -> {
                    navActions.popBackStack()
                }
            }
        }
        .launchIn(coroutineScope)
}