plugins {
    id("finances.android.library")
    id("kotlin-parcelize")
}

android {
    namespace = "serg.chuprin.finances.core.currency.choice.api"
}

dependencies {
    implementation(project(":core:api"))

    // --- Architecture & Async ---
    implementation(libs.bundles.coroutines)

    // --- UI ---
    implementation(libs.bundles.androidx.ui)
    implementation(libs.adapterDelegates)
    
    // Falls das Modul explizit Navigation oder Lifecycle braucht, füge diese Bundles hinzu:
    // implementation(libs.bundles.androidx.navigation)
    // implementation(libs.bundles.androidx.lifecycle)
    
    // Im alten File waren AndroidX Core, Design, Fragment, AppCompat, Transition, ConstraintLayout gelistet.
    // Diese sind alle im 'androidx-ui' Bundle enthalten.
    
    // Spezifisch: Transition war im alten File explizit. 
    // Ist oft transitiv in Material dabei, aber hier zur Sicherheit:
    implementation("androidx.transition:transition:1.4.1")
}