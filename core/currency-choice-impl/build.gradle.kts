plugins {
    id("finances.android.library")
    id("kotlin-parcelize")
    // Kapt wird für Dagger benötigt
    id("kotlin-kapt")
}

android {
    namespace = "serg.chuprin.finances.core.currency.choice.impl"
}

dependencies {
    // Interne Abhängigkeiten
    implementation(project(":core:api"))
    implementation(project(":core:currency-choice-api"))

    // --- Architecture & Async ---
    implementation(libs.bundles.coroutines)
    implementation(libs.bundles.androidx.lifecycle)

    // --- UI & Navigation ---
    implementation(libs.bundles.androidx.ui)
    implementation(libs.bundles.androidx.navigation)
    implementation(libs.coil)
    implementation(libs.adapterDelegates)

    // --- Dependency Injection (Dagger) ---
    implementation(libs.dagger)
    kapt(libs.dagger.compiler)

    // --- Testing ---
    testImplementation(project(":core:test"))
    // Bundle für Unit-Tests (JUnit5, MockK, etc.)
    testImplementation(libs.bundles.test.unit)
    // JUnit 5 Runtime Engine wird für die Ausführung benötigt
    testRuntimeOnly(libs.junit5.engine)
}