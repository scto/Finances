plugins {
  id("finances.android.application")
  id("finances.android.compose")
  // Falls du Hilt verwendest, müsste hier auch das Hilt-Plugin stehen,
  // oder es ist bereits im finances.android.application enthalten.
  // id("dagger.hilt.android.plugin")
}

android {
  namespace = "serg.chuprin.finances"

  defaultConfig {
    applicationId = "serg.chuprin.finances"
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    vectorDrawables {
      useSupportLibrary = true
    }
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(
        getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro"
      )
    }
  }

  // Kotlin Options und CompileSdk werden nun vom Plugin gesteuert

  packaging {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }
}

dependencies {
  // Modul-Abhängigkeiten
  implementation(project(":feature:onboarding"))
  implementation(project(":feature:dashboard"))
  implementation(project(":feature:categories-list"))
  implementation(project(":feature:money-account"))
  implementation(project(":feature:money-accounts-list"))
  implementation(project(":feature:money-account-details"))
  implementation(project(":feature:transaction"))
  implementation(project(":feature:transactions-report"))
  implementation(project(":feature:user-profile"))
  implementation(project(":feature:authorization"))
  implementation(project(":feature:dashboard-setup-impl"))

  implementation(project(":core:api"))
  implementation(project(":core:impl"))
  implementation(project(":core:mvi"))
  implementation(project(":core:firebase"))

  // Library Abhängigkeiten (Beispiele, basierend auf typischen Apps)
  implementation(libs.core.ktx)
  implementation(libs.lifecycle.runtime.ktx)
  implementation(libs.activity.compose)
  implementation(platform(libs.compose.bom))
  implementation(libs.ui)
  implementation(libs.ui.graphics)
  implementation(libs.ui.tooling.preview)
  implementation(libs.material3)

  // Navigation
  implementation(libs.navigation.fragment.ktx)
  implementation(libs.navigation.ui.ktx)

  // DI
  implementation(libs.dagger)
  kapt(libs.dagger.compiler)
}