plugins {
    alias(libs.plugins.gwasuwon.app.core)
    alias(libs.plugins.gwasuwon.app.compose)
}

android {
    namespace = "com.prography.gwasuwon"

    defaultConfig {
        applicationId = "com.prography.gwasuwon"
        versionCode = 1
        versionName = "1.0"
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":feature:account"))
    implementation(project(":core:domain"))
    implementation(project(":core:configuration"))
    implementation(project(":core:utils"))
    implementation(project(":core:navigation"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.viewModel)
    implementation(libs.androidx.navigation)
    testImplementation(libs.junit)
}