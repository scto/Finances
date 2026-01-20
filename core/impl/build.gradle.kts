plugins {
    // Verwendet das zentrale Convention Plugin für Android Libraries
    // Dies setzt minSdk, compileSdk, Java-Versionen und Kotlin-Optionen automatisch.
    id("finances.android.library")
    id("kotlin-parcelize")
}

android {
    namespace = "serg.chuprin.finances.core.api"
}

dependencies {
    // Interne Modul-Abhängigkeit
    api(project(":core:mvi"))

    // --- Architecture & Async ---
    implementation(libs.bundles.coroutines)
    implementation(libs.bundles.androidx.lifecycle)
    
    // javax.inject wird für Dagger Interfaces benötigt (oft für @Inject Annotationen in Interfaces)
    implementation("javax.inject:javax.inject:1")

    // --- UI & Navigation ---
    // Das 'androidx-ui' Bundle ersetzt: Core KTX, AppCompat, Fragment, Material, ConstraintLayout, etc.
    implementation(libs.bundles.androidx.ui)
    
    // Navigation Bundle (Fragment & UI KTX)
    implementation(libs.bundles.androidx.navigation)
    
    // Spezifische UI Libraries
    implementation(libs.coil)
    implementation(libs.adapterDelegates)
    
    // Transition war explizit im alten Build-File. 
    // Es ist oft transitiv in Material enthalten, aber hier zur Sicherheit explizit:
    implementation("androidx.transition:transition:1.4.1")

    // Hinweis: 'Libraries.Coroutines.Bindings' war in der alten Datei. 
    // Falls das FlowBinding ist und du es oft nutzt, solltest du es in die toml aufnehmen.
    // implementation(libs.flowbinding) 

    // --- Logging ---
    // 'Libraries.TIMBER' war definiert als 'timberkt'
    api(libs.timber.kt) 
}