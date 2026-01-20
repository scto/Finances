plugins {
    id("finances.android.library")
}

android {
    namespace = "serg.chuprin.finances.core.piechart"
}

dependencies {
    // UI Bundle
    // Enthält AndroidX Core, AppCompat und andere UI-Grundlagen, die hier benötigt werden
    implementation(libs.bundles.androidx.ui)

    // Logging (TimberKt)
    implementation(libs.timber.kt)
}