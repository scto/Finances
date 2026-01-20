plugins {
    id("finances.android.library")
    id("kotlin-parcelize")
    id("kotlin-kapt")
    // Serialization Plugin
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "serg.chuprin.finances.core.impl"

    defaultConfig {
        // Diese Felder wurden im alten Skript gesetzt.
        // Da wir nun 'build-logic' nutzen, könnten wir sie zentralisieren, 
        // aber hier hardcoden wir sie erstmal sicherheitshalber oder nutzen Dummy-Werte,
        // da AppConfig nicht mehr existiert.
        buildConfigField("int", "VERSION_CODE", "1")
        buildConfigField("String", "VERSION_NAME", "\"1.0.0\"")
    }

    buildTypes {
        // Die Convention Plugins erstellen 'debug' und 'release'.
        // Hier fügen wir 'dev' hinzu, wie im alten Skript.
        create("dev") {
            initWith(getByName("debug"))
        }
    }

    // SourceSets für Debug/Dev Menüs (Beagle)
    sourceSets {
        getByName("dev").java.srcDir("src/common/kotlin")
        getByName("debug").java.srcDir("src/common/kotlin")
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    
    // Interne Module
    api(project(":core:api"))
    api(project(":core:firebase"))
    
    // --- Architecture & Async ---
    implementation(libs.bundles.coroutines)
    implementation(libs.bundles.androidx.lifecycle)
    
    // --- Serialization ---
    implementation(libs.kotlin.serialization.json)

    // --- UI & Navigation ---
    implementation(libs.bundles.androidx.ui)
    implementation(libs.bundles.androidx.navigation)
    implementation(libs.coil)
    implementation(libs.adapterDelegates)
    
    // Preferences (ersetzt Libraries.Preferences.LIBRARY)
    implementation(libs.androidx.preference.ktx)

    // --- Dependency Injection (Dagger) ---
    kapt(libs.dagger.compiler)
    implementation(libs.dagger)

    // --- Debug Menu (Beagle) ---
    // Dev & Debug nutzen die echte Implementierung
    "devImplementation"(libs.beagle.drawer)
    "debugImplementation"(libs.beagle.drawer)
    // Release nutzt die No-Op (keine Funktion) Version
    "releaseImplementation"(libs.beagle.noop)

    // --- Testing ---
    testImplementation(project(":core:test"))
    testImplementation(libs.bundles.test.unit)
    testRuntimeOnly(libs.junit5.engine)
}