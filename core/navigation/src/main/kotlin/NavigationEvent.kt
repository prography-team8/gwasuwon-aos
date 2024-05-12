import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * Created by MyeongKi.
 */
sealed interface NavigationEvent {
    data class NavigateSample2(
        val currentCount: Int
    ) : NavigationEvent

    data object popBack : NavigationEvent
}

fun MutableSharedFlow<NavigationEvent>.subscribeNavigationEvent(
    navActions: NavigationActions,
    coroutineScope: CoroutineScope
): Job {
    return this
        .onEach {
            when (it) {
                is NavigationEvent.NavigateSample2 -> {
                    navActions.navigateSample2(it.currentCount)
                }

                is NavigationEvent.popBack -> {
                    navActions.popBackStack()
                }
            }
        }
        .launchIn(coroutineScope)
}