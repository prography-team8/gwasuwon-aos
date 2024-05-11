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
