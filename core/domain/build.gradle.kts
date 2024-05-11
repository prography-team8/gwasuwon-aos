plugins {
    alias(libs.plugins.gwasuwon.lib.android.core)
}

android {
    namespace = "com.prography.domain"
    kotlinOptions {
        jvmTarget = "1.8"
    }
}
dependencies {
    implementation(libs.kotlin.coroutines)
}