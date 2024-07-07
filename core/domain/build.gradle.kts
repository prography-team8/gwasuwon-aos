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
    implementation(project(":core:utils"))
    implementation(libs.androidx.compose.paging)
    implementation(libs.kotlin.coroutines)
    implementation(libs.kotlin.date)

}