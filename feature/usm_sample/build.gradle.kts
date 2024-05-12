plugins {
    alias(libs.plugins.gwasuwon.lib.android.core)
    alias(libs.plugins.gwasuwon.lib.compose)
}

android {
    namespace = "com.prography.usm_sample"
    kotlinOptions {
        jvmTarget = "1.8"
    }
}
dependencies {
    implementation(project(":core:usm"))
    implementation(project(":core:configuration"))
    implementation(project(":core:domain"))
    implementation(project(":core:navigation"))
}