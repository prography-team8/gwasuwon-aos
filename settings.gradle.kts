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
    }
}

rootProject.name = "gwasuwon-aos"
include(":app")
include(":feature:login")
include(":core:domain")
include(":core:network")
include(":core:utils")
include(":feature:lesson")
include(":core:ui")
include(":core:navigation")
include(":core:usm")
include(":feature:usm_sample")
