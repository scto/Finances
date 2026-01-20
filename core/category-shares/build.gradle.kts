plugins {
    id("finances.android.library")
    id("kotlin-parcelize")
}

android {
    namespace = "serg.chuprin.finances.core.categories.shares"
}

dependencies {
    // Interne Modul-Abhängigkeiten
    implementation(project(":core:api"))
    api(project(":core:pie-chart"))

    // UI Bundle
    // Enthält: AndroidX Core, AppCompat, Material, Flexbox (wurde im Bundle integriert)
    implementation(libs.bundles.androidx.ui)

    // Adapter Delegates
    implementation(libs.adapterDelegates)
}