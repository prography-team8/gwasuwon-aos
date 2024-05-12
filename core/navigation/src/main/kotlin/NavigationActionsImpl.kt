import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

/**
 * Created by MyeongKi.
 */
class NavigationActions(
    private val navController: NavHostController, private val onEmptyBackStack: () -> Unit
) {
    fun navigateSample1() {
        navController.navigate(GwasuwonPath.Sample1Path.getNavigation()) {
            popUpTo(navController.currentDestination?.id ?: navController.graph.findStartDestination().id) {
                saveState = true
            }
        }
    }

    fun navigateSample2(currentCount: Int) {
        navController.navigate(GwasuwonPath.Sample2Path(currentCount).getNavigation()) {
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