plugins {
    id("finances.android.library")
}

android {
    namespace = "serg.chuprin.finances.core.test.presentation.mvi.utils"
}

dependencies {
    // Coroutines Core
    implementation(libs.kotlin.coroutines.core)
    
    // Coroutines Test via API, damit Module, die :core:test einbinden, 
    // Zugriff auf runTest, TestDispatcher etc. haben.
    api(libs.kotlin.coroutines.test)
    
    // Coroutines Debug Agent (War Libraries.Tests.COROUTINES_DEBUG)
    // Da wir die Version zentral haben, nutzen wir die Referenz
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-debug:${libs.versions.coroutines.get()}")

    // Logging
    implementation(libs.timber.kt)
}