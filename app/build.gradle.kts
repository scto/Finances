import serg.chuprin.finances.config.AppConfig

import serg.chuprin.finances.config.enableBuildConfig
import serg.chuprin.finances.config.enableViewBinding 
import serg.chuprin.finances.config.enableCompose

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    // Replaced deprecated 'android.extensions' with 'kotlin-parcelize'
    id("kotlin-parcelize")
    id("ru.cleverpumpkin.proguard-dictionaries-generator")
    id("androidx.navigation.safeargs")
}

android {
    namespace = AppConfig.APPLICATION_ID
    
    defaultConfig {
        versionCode = AppConfig.VERSION_CODE
        versionName = AppConfig.VERSION_NAME
        applicationId = AppConfig.APPLICATION_ID
    }
    /*
    signingConfigs {
        create(AppConfig.BuildTypes.DEBUG.name) {
            keyPassword = "android"
            storePassword = "android"
            keyAlias = "androiddebugkey"
            storeFile = File(projectDir, "debug.keystore")
        }
    }
    */
    buildTypes {
        maybeCreate(AppConfig.BuildTypes.DEBUG.name).apply {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "+${getLastCommitHash()}"
            //signingConfig = signingConfigs.getByName(AppConfig.BuildTypes.DEBUG.name)
        }
        maybeCreate(AppConfig.BuildTypes.DEV.name).apply {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "+${getLastCommitHash()}"
            //signingConfig = signingConfigs.getByName(AppConfig.BuildTypes.DEBUG.name)
        }
    }
    //enableBuildConfig()
    //enableViewBinding()
    
    /*
    buildFeatures {
        //buildConfig = true
        viewBinding = true
    }
    */
    
}

proguardDictionaries {
    dictionaryNames = listOf(
        "build/class-dictionary",
        "build/method-dictionary"
    )
    minLineLength = 10
    maxLineLength = 20
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf(".*jar"))))

    // region Modules.
    implementation(project(":core:impl"))
    implementation(project(":feature:dashboard"))
    implementation(project(":feature:onboarding"))
    implementation(project(":feature:transaction"))
    implementation(project(":feature:user-profile"))
    implementation(project(":feature:transactions-report"))
    implementation(project(":feature:authorization"))
    implementation(project(":feature:money-accounts-list"))
    implementation(project(":feature:categories-list"))
    implementation(project(":feature:dashboard-setup-api"))
    implementation(project(":feature:dashboard-setup-impl"))
    implementation(project(":feature:money-account-details"))
    implementation(project(":feature:money-account"))
    implementation(project(":core:currency-choice-api"))
    implementation(project(":core:currency-choice-impl"))
    // endregion

    implementation(Libraries.KOTLIN)
    implementation(Libraries.Coroutines.CORE)
    implementation(Libraries.Coroutines.ANDROID)

    // region DI.
    kapt(Libraries.Dagger.COMPILER)
    implementation(Libraries.Dagger.LIBRARY)
    // endregion

    // region UI.
    implementation(Libraries.COIL)

    // Navigation.
    implementation(Libraries.Android.Navigation)

    // Android.
    implementation(Libraries.Android.CORE)
    implementation(Libraries.Android.DESIGN)
    implementation(Libraries.Android.FRAGMENT)
    implementation(Libraries.Android.APPCOMPAT)
    implementation(Libraries.Android.CONSTRAINT_LAYOUT)
    // endregion

    // Architecture components.
    implementation(Libraries.Android.Lifecycle)

    // Firebase.
    implementation(Libraries.Infrastructure.AUTH)
    implementation(Libraries.Infrastructure.FIRESTORE)
}

fun getLastCommitHash(): String {
    return try {
        // Use standard ProcessBuilder to avoid Gradle 'exec' context issues
        val process = ProcessBuilder("git", "rev-parse", "--short", "HEAD").start()
        process.inputStream.bufferedReader().use { it.readText().trim() }
    } catch (e: Exception) {
        // Fallback if git is not available or fails
        "unknown"
    }
}