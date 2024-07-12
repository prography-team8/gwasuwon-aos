plugins {
    alias(libs.plugins.gwasuwon.lib.android.core)
    id("app.cash.sqldelight") version libs.versions.sql.delight

}
sqldelight {
    databases {
        create("GwasuwonDialogSqlDelightDatabase") {
            packageName.set("com.prography.database.dialog")
        }
    }
}

android {
    namespace = "com.prography.database"
    kotlinOptions {
        jvmTarget = "1.8"
    }
}
dependencies {
    implementation(project(":core:domain"))
    implementation(project(":core:utils"))
    implementation(libs.sql.delight.core)
    implementation(libs.sql.delight.android)

}