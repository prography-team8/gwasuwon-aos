import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

/**
 * Created by MyeongKi.
 */
class NavigationActions(
    private val navController: NavHostController, private val onEmptyBackStack: () -> Unit
) {
    fun navigateSignUpRoute() {
        navController.navigate(GwasuwonPath.SingUpPath.getNavigation()) {
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

    fun navigateLessonsRoute() {
        navController.navigate(GwasuwonPath.LessonsPath.getNavigation()) {
            launchSingleTop = true
            popUpTo(navController.currentDestination?.id ?: navController.graph.findStartDestination().id) {
                inclusive = true
                saveState = true
            }
        }
    }

    fun navigateCreateLessonRoute() {
        navController.navigate(GwasuwonPath.CrateLessonPath.getNavigation()) {
            popUpTo(navController.currentDestination?.id ?: navController.graph.findStartDestination().id) {
                saveState = true
            }
        }
    }

    fun navigateLessonDetailRoute(lessonId: Long) {
        navController.navigate(GwasuwonPath.LessonDetailPath(lessonId).getNavigation()) {
            popUpTo(navController.currentDestination?.id ?: navController.graph.findStartDestination().id) {
                saveState = true
            }
        }
    }

    fun navigateSuccessCreateLessonRoute(lessonId: Long) {
        navController.navigate(GwasuwonPath.SuccessCreateLessonPath(lessonId).getNavigation()) {
            popUpTo(navController.currentDestination?.id ?: navController.graph.findStartDestination().id) {
                inclusive = true
                saveState = true
            }
        }
    }

    fun navigateLessonInfoDetailRoute(lessonId: Long) {
        navController.navigate(GwasuwonPath.LessonInfoDetailPath(lessonId).getNavigation()) {
            popUpTo(navController.currentDestination?.id ?: navController.graph.findStartDestination().id) {
                saveState = true
            }
        }
    }

    fun navigateInviteStudentQrRoute(lessonId: Long) {
        navController.navigate(GwasuwonPath.InviteStudentQrPath(lessonId).getNavigation()) {
            popUpTo(navController.currentDestination?.id ?: navController.graph.findStartDestination().id) {
                saveState = true
            }
        }
    }

    fun navigateLessonContractQrRoute(lessonId: Long) {
        navController.navigate(GwasuwonPath.LessonContractQrPath(lessonId).getNavigation()) {
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