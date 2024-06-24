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
    implementation(project(":core:configuration"))
    implementation(libs.kotlin.collections)

}