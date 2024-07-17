plugins {
    alias(libs.plugins.gwasuwon.lib.android.core)
    kotlin("plugin.serialization")

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
    implementation(libs.ktor.client.serialization)

}