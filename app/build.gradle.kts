plugins {
    id("finances.android.application")
    // Aktiviert Compose (da enableCompose im alten Skript importiert wurde)
    id("finances.android.compose")
    id("kotlin-parcelize")
    id("kotlin-kapt")
    
    // Navigation SafeArgs & Proguard (jetzt über Alias)
    alias(libs.plugins.androidx.navigation.safeargs)
    alias(libs.plugins.proguard.dictionaries)
}

android {
    namespace = "serg.chuprin.finances"

    defaultConfig {
        applicationId = "serg.chuprin.finances"
        versionCode = 1
        versionName = "1.0.0"
    }

    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        val debug by getting {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "+${getLastCommitHash()}"
        }

        create("dev") {
            initWith(debug)
            applicationIdSuffix = ".dev"
            // Falls du spezifische SigningConfigs hast, füge sie hier hinzu
        }

        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    
    // Proguard Dictionaries Konfiguration (optional, falls benötigt)
    // proguardDictionaries { ... }
}

dependencies {
    // --- Feature Modules ---
    implementation(project(":feature:onboarding"))
    implementation(project(":feature:authorization"))
    implementation(project(":feature:dashboard"))
    implementation(project(":feature:dashboard-setup-api"))
    implementation(project(":feature:dashboard-setup-impl"))
    implementation(project(":feature:categories-list"))
    implementation(project(":feature:money-account"))
    implementation(project(":feature:money-account-details"))
    implementation(project(":feature:money-accounts-list"))
    implementation(project(":feature:transaction"))
    implementation(project(":feature:transactions-report"))
    implementation(project(":feature:user-profile"))

    // --- Core Modules ---
    implementation(project(":core:api"))
    implementation(project(":core:impl"))
    implementation(project(":core:mvi"))
    implementation(project(":core:firebase"))
    implementation(project(":core:pie-chart"))
    implementation(project(":core:category-shares"))
    implementation(project(":core:currency-choice-api"))
    implementation(project(":core:currency-choice-impl"))

    // --- Architecture & Async ---
    implementation(libs.bundles.coroutines)
    implementation(libs.bundles.androidx.lifecycle)

    // --- DI ---
    implementation(libs.dagger)
    kapt(libs.dagger.compiler)

    // --- UI & Navigation ---
    implementation(libs.bundles.androidx.ui)
    implementation(libs.bundles.androidx.navigation)
    implementation(libs.coil)

    // --- Firebase ---
    implementation(platform(libs.firebase.bom))
    implementation(libs.bundles.firebase)
}

/**
 * Hilfsfunktion um den Git Hash für die Versionierung zu holen.
 */
fun getLastCommitHash(): String {
    return try {
        val process = ProcessBuilder("git", "rev-parse", "--short", "HEAD").start()
        process.inputStream.bufferedReader().use { it.readText().trim() }
    } catch (e: Exception) {
        "unknown"
    }
}