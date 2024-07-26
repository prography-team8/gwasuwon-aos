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

    data object LessonsPath : GwasuwonPath {
        override val routeHost: GwasuwonRouteHost = GwasuwonRouteHost.LESSONS
        override val arguments: List<NamedNavArgument> = listOf()
    }

    data object LessonInvitedPath : GwasuwonPath {
        override val routeHost: GwasuwonRouteHost = GwasuwonRouteHost.LESSON_INVITED
        override val arguments: List<NamedNavArgument> = listOf()
    }

    data object CrateLessonPath : GwasuwonPath {
        override val routeHost: GwasuwonRouteHost = GwasuwonRouteHost.CREATE_LESSON
        override val arguments: List<NamedNavArgument> = listOf()
    }

    data class LessonDetailPath(
        private val lessonId: Long = INVALID_ID
    ) : GwasuwonPath {
        override val routeHost: GwasuwonRouteHost = GwasuwonRouteHost.LESSON_DETAIL
        override val arguments: List<NamedNavArgument> = listOf(
            navArgument(ArgumentName.LESSON_ID.name) { defaultValue = lessonId },
        )

        enum class ArgumentName {
            LESSON_ID,
            ;
        }

        companion object {
            const val INVALID_ID = -1L
        }
    }

    data class ExtensionLessonPath(
        private val lessonId: Long = -1
    ) : GwasuwonPath {
        override val routeHost: GwasuwonRouteHost = GwasuwonRouteHost.EXTENSION_LESSON
        override val arguments: List<NamedNavArgument> = listOf(
            navArgument(ArgumentName.LESSON_ID.name) { defaultValue = lessonId },
        )

        enum class ArgumentName {
            LESSON_ID,
            ;
        }
    }


    data class SuccessCreateLessonPath(
        private val lessonId: Long = -1
    ) : GwasuwonPath {
        override val routeHost: GwasuwonRouteHost = GwasuwonRouteHost.SUCCESS_CREATE_LESSON
        override val arguments: List<NamedNavArgument> = listOf(
            navArgument(ArgumentName.LESSON_ID.name) { defaultValue = lessonId },
        )

        enum class ArgumentName {
            LESSON_ID,
            ;
        }
    }

    data class LessonInfoDetailPath(
        private val lessonId: Long = -1
    ) : GwasuwonPath {
        override val routeHost: GwasuwonRouteHost = GwasuwonRouteHost.LESSON_INFO_DETAIL
        override val arguments: List<NamedNavArgument> = listOf(
            navArgument(ArgumentName.LESSON_ID.name) { defaultValue = lessonId },
        )

        enum class ArgumentName {
            LESSON_ID,
            ;
        }
    }

    data class InviteStudentQrPath(
        private val lessonId: Long = -1
    ) : GwasuwonPath {
        override val routeHost: GwasuwonRouteHost = GwasuwonRouteHost.INVITE_STUDENT_QR
        override val arguments: List<NamedNavArgument> = listOf(
            navArgument(ArgumentName.LESSON_ID.name) { defaultValue = lessonId },
        )

        enum class ArgumentName {
            LESSON_ID,
            ;
        }
    }

    data class LessonCertificationQrPath(
        private val lessonId: Long = -1
    ) : GwasuwonPath {
        override val routeHost: GwasuwonRouteHost = GwasuwonRouteHost.LESSON_CERTIFICATION_QR
        override val arguments: List<NamedNavArgument> = listOf(
            navArgument(ArgumentName.LESSON_ID.name) { defaultValue = lessonId },
        )

        enum class ArgumentName {
            LESSON_ID,
            ;
        }
    }

    data class LessonContractQrPath(
        private val lessonId: Long = -1
    ) : GwasuwonPath {
        override val routeHost: GwasuwonRouteHost = GwasuwonRouteHost.LESSON_CONTRACT_QR
        override val arguments: List<NamedNavArgument> = listOf(
            navArgument(ArgumentName.LESSON_ID.name) { defaultValue = lessonId },
        )

        enum class ArgumentName {
            LESSON_ID,
            ;
        }
    }
}