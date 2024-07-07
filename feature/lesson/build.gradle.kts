plugins {
    alias(libs.plugins.gwasuwon.lib.android.core)
    alias(libs.plugins.gwasuwon.lib.compose)
}

android {
    namespace = "com.prography.lesson"
    kotlinOptions {
        jvmTarget = "1.8"
    }
}
dependencies {
    implementation(project(":core:ui"))
    implementation(project(":core:usm"))
    implementation(project(":core:domain"))
    implementation(project(":core:navigation"))
    implementation(project(":core:utils"))
    implementation(libs.androidx.compose.paging)
    implementation(libs.kotlin.collections)
    implementation(libs.kotlin.date)
    implementation(libs.kizitonwose.calendar)
}