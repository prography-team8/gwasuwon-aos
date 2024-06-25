plugins {
    alias(libs.plugins.gwasuwon.lib.android.core)
}

android {
    namespace = "com.prography.utils"
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(libs.security.crypto)
    implementation(libs.kotlin.date)

}