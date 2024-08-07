pluginManagement {
    includeBuild("build-logic")

    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = java.net.URI("https://devrepo.kakao.com/nexus/content/groups/public/") }

    }
}

rootProject.name = "gwasuwon-aos"
include(":app")

include(":feature:account")
include(":feature:lesson")
include(":feature:qr")

include(":core:domain")
include(":core:network")
include(":core:database")
include(":core:utils")
include(":core:ui")
include(":core:navigation")
include(":core:usm")
