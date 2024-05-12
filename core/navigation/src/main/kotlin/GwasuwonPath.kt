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

    data object Sample1Path : GwasuwonPath {
        override val routeHost: GwasuwonRouteHost = GwasuwonRouteHost.SAMPLE_HOST
        override val arguments: List<NamedNavArgument> = listOf()
    }

    data class Sample2Path(val currentCount: Int) : GwasuwonPath {
        override val routeHost: GwasuwonRouteHost = GwasuwonRouteHost.SAMPLE_HOST2
        override val arguments: List<NamedNavArgument> =
            listOf(navArgument(ArgumentName.CURRENT_COUNT.name) { defaultValue = currentCount })

        enum class ArgumentName {
            CURRENT_COUNT
        }
    }
}