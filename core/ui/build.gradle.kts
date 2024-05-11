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
