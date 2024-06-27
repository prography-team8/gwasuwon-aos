plugins {
    alias(libs.plugins.gwasuwon.lib.android.core)
    alias(libs.plugins.gwasuwon.lib.compose)
}

android {
    namespace = "com.prography.qr"
    kotlinOptions {
        jvmTarget = "1.8"
    }
}
dependencies {
    implementation(project(":core:ui"))
    implementation(project(":core:usm"))
    implementation(project(":core:domain"))
    implementation(project(":core:navigation"))

}