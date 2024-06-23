import androidx.navigation.NamedNavArgument
import androidx.navigation.navArgument

/**
 * Created by MyeongKi.
 */
sealed interface GwasuwonPath {
    val routeHost: GwasuwonRouteHost
    val arguments: List<NamedNavArgument>
    fun getDestination(): String {
        val queries: String =
            arguments.map { "${it.name}={${it.name}}" }.takeIf { it.isNotEmpty() }?.fold("") { acc, s ->
                if (acc.isEmpty()) {
                    "?$s"
                } else {
                    "$acc&$s"
                }
            } ?: ""
        return routeHost.name + queries
    }

    fun getNavigation(): String {
        val queries: String =
            arguments.map { "${it.name}=${it.argument.defaultValue}" }.takeIf { it.isNotEmpty() }?.fold("") { acc, s ->
                if (acc.isEmpty()) {
                    "?$s"
                } else {
                    "$acc&$s"
                }
            } ?: ""
        return routeHost.name + queries

    }

    data object SingInPath : GwasuwonPath {
        override val routeHost: GwasuwonRouteHost = GwasuwonRouteHost.SIGN_IN
        override val arguments: List<NamedNavArgument> = listOf()
    }
    data object SingUpPath : GwasuwonPath {
        override val routeHost: GwasuwonRouteHost = GwasuwonRouteHost.SIGN_UP
        override val arguments: List<NamedNavArgument> = listOf()
    }

    data object LessonPath : GwasuwonPath {
        override val routeHost: GwasuwonRouteHost = GwasuwonRouteHost.LESSON
        override val arguments: List<NamedNavArgument> = listOf()
    }
}