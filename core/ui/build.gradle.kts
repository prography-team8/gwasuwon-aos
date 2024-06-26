plugins {
    alias(libs.plugins.gwasuwon.lib.android.core)
    alias(libs.plugins.gwasuwon.lib.compose)
}

android {
    namespace = "com.prography.ui"
    kotlinOptions {
        jvmTarget = "1.8"
    }
}
dependencies{
    implementation(project(":core:domain"))
    implementation(project(":core:utils"))
    implementation(libs.kotlin.collections)
    implementation(libs.kotlin.date)
    implementation(libs.kotlin.coroutines)
    implementation(libs.androidx.compose.viewModel)
    implementation(libs.android.systemuicontroller)

}