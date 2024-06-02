plugins {
    alias(libs.plugins.gwasuwon.lib.android.core)
    kotlin("plugin.serialization")

}

android {
    namespace = "com.prography.network"
    kotlinOptions {
        jvmTarget = "1.8"
    }
}
dependencies {
    implementation(project(":core:domain"))
    implementation(project(":core:utils"))

    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.negotiation)
    implementation(libs.ktor.client.serialization)
    implementation(libs.ktor.client.okhttp)
}