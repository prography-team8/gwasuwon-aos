import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "com.prography.gwasuwon.buildlogic"

// Configure the build-logic plugins to target JDK 17
// This matches the JDK used to build the project, and is not related to what is running on device.
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)

}
tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("appCorePlugin") {
            id = "gwasuwon.app.core"
            implementationClass = "AppCorePlugin"
        }
        register("appComposePlugin") {
            id = "gwasuwon.app.compose"
            implementationClass = "AppComposePlugin"
        }
        register("libComposePlugin") {
            id = "gwasuwon.lib.compose"
            implementationClass = "LibComposePlugin"
        }
        register("libAndroidCorePlugin") {
            id = "gwasuwon.lib.android.core"
            implementationClass = "LibAndroidCorePlugin"
        }
    }
}