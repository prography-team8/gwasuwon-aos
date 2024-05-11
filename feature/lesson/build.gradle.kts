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
