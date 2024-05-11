plugins {
    alias(libs.plugins.gwasuwon.lib.android.core)
    alias(libs.plugins.gwasuwon.lib.compose)
}

android {
    namespace = "com.prography.configuration"
    kotlinOptions {
        jvmTarget = "1.8"
    }
}
dependencies {
    implementation(project(":core:domain"))
    implementation(libs.kotlin.coroutines)
    implementation(libs.androidx.compose.viewModel)
}