plugins {
    // Verwendet das zentrale Convention Plugin für Android Libraries
    id("finances.android.library")
    // Parcelize muss separat angewendet werden, da es nicht jedes Modul braucht
    id("kotlin-parcelize")
}

android {
    namespace = "serg.chuprin.finances.core.api"
    
    // 'compileSdk', 'minSdk' und 'kotlinOptions' kommen automatisch vom Plugin.
}

dependencies {
    api(project(":core:mvi"))

    // --- Architecture & Async ---
    implementation(libs.bundles.coroutines)
    implementation(libs.bundles.androidx.lifecycle)
    
    // javax.inject wird für Dagger Interfaces benötigt (nicht im Bundle, da spezifisch)
    implementation("javax.inject:javax.inject:1")

    // --- UI & Navigation ---
    // Das 'androidx-ui' Bundle ersetzt Core, AppCompat, Fragment, Material, ConstraintLayout, etc.
    implementation(libs.bundles.androidx.ui)
    
    // Navigation Bundle (Fragment & UI KTX)
    implementation(libs.bundles.androidx.navigation)
    
    // Spezifische UI Libraries
    implementation(libs.coil)
    implementation(libs.adapterDelegates)
    
    // Transition (war explizit im alten Build-File, oft transitiv in Material enthalten, 
    // aber hier sicherheitshalber explizit, falls benötigt)
    implementation("androidx.transition:transition:1.4.1")

    // --- Logging ---
    // Im alten File war es 'Libraries.TIMBER' -> 'timberkt'. 
    // Falls du auf das reine JakeWharton Timber gewechselt bist, nutze 'libs.timber'.
    api(libs.timber.kt) 
}