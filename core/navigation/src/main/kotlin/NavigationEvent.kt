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
    data class NavigateSignUpRoute(
        val socialLoginType: String,
        val accessKey: String
    ) : NavigationEvent

    data object NavigateLessonRoute : NavigationEvent
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

                is NavigationEvent.NavigateLessonRoute -> {
                    navActions.navigateLessounRoute()

                }

                is NavigationEvent.NavigateSignUpRoute -> {
                    navActions.navigateSignUpRoute(
                        socialLoginType = it.socialLoginType,
                        accessKey = it.accessKey
                    )

                }

                is NavigationEvent.PopBack -> {
                    navActions.popBackStack()
                }
            }
        }
        .launchIn(coroutineScope)
}