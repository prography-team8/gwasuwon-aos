import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

/**
 * Created by MyeongKi.
 */
class NavigationActions(
    private val navController: NavHostController, private val onEmptyBackStack: () -> Unit
) {
    fun navigateSignUpRoute(
        socialLoginType: String,
        accessKey: String
    ) {
        navController.navigate(GwasuwonPath.SingUpPath(
            socialLoginType = socialLoginType,
            accessKey = accessKey
        ).getNavigation()) {
            popUpTo(navController.currentDestination?.id ?: navController.graph.findStartDestination().id) {
                saveState = true
            }
        }
    }

    fun navigateSignInRoute() {
        navController.navigate(GwasuwonPath.SingInPath.getNavigation()) {
            popUpTo(navController.currentDestination?.id ?: navController.graph.findStartDestination().id) {
                saveState = true
            }
        }
    }

    fun navigateLessounRoute() {
        navController.navigate(GwasuwonPath.LessonPath.getNavigation()) {
            popUpTo(navController.currentDestination?.id ?: navController.graph.findStartDestination().id) {
                saveState = true
            }
        }
    }


    fun popBackStack() {
        if (!navController.popBackStack()) {
            onEmptyBackStack()
        }
    }
}