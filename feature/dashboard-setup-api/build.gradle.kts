plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    namespace = "serg.chuprin.finances.feature.dashboard.setup"
}

dependencies {
    implementation(project(":core:api"))
    implementation(Libraries.KOTLIN)
}