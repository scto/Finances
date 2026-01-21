plugins {
    id("finances.android.application")
    id("finances.android.compose")
    id("finances.android.hilt")
    id("finances.android.firebase")
    id("finances.code.quality.detekt")
}

android {
    defaultConfig {
        applicationId = "serg.chuprin.finances"
        versionCode = 1
        versionName = "0.1"

        testInstrumentationRunner = "serg.chuprin.finances.core.test.FinancesTestRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            applicationIdSuffix = ".debug"
        }
    }
    
    // Namespace ist wichtig für R.class Generierung
    namespace = "serg.chuprin.finances.app"
}

dependencies {
    // Features werden hier konsumiert
    implementation(project(":feature:dashboard"))
    implementation(project(":feature:transaction"))
    implementation(project(":feature:money-accounts-list"))
    implementation(project(":feature:money-account"))
    implementation(project(":feature:money-account-details"))
    implementation(project(":feature:categories-list"))
    implementation(project(":feature:transactions-report"))
    implementation(project(":feature:user-profile"))
    implementation(project(":feature:onboarding"))
    implementation(project(":feature:authorization"))
    implementation(project(":feature:dashboard-setup-api"))
    implementation(project(":feature:dashboard-setup-impl"))

    // Core Module
    implementation(project(":core:api"))
    // HIER muss core:impl eingebunden werden, damit Hilt es findet
    implementation(project(":core:impl")) 
    implementation(project(":core:mvi"))
    implementation(project(":core:firebase"))
    implementation(project(":core:pie-chart"))
    implementation(project(":core:category-shares"))
    implementation(project(":core:currency-choice-api"))
    implementation(project(":core:currency-choice-impl"))

    implementation(libs.timber)
}