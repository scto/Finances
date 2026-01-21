/*
 * Copyright 2025 Thomas Schmid
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

import org.gradle.kotlin.dsl.compileOnly
import org.gradle.kotlin.dsl.gradlePlugin
import org.gradle.kotlin.dsl.libs
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "serg.chuprin.build.logic"

val javaVersion = libs.versions.java.get().toInt()
//val javaVersion = javaVersion("java").toInt()

java {
    sourceCompatibility = JavaVersion.values()[javaVersion - 1]
    targetCompatibility = JavaVersion.values()[javaVersion - 1]
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.valueOf("JVM_$javaVersion"))
    }
}

// Abhängigkeiten für die Plugins selbst (Kompilierzeit)
dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
    compileOnly(libs.firebase.crashlytics.gradlePlugin)
    compileOnly(libs.firebase.perf.gradlePlugin) // Optional, falls genutzt
    compileOnly(libs.dokka.gradlePlugin)
    compileOnly(libs.spotless.gradlePlugin)
    // Detekt Plugin (for DetektConventionPlugin)
    compileOnly(libs.detekt.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("application") {
            id = "serg.chuprin.application"
            implementationClass = "ApplicationConventionPlugin"
        }
        register("library") {
            id = "serg.chuprin.library"
            implementationClass = "LibraryConventionPlugin"
        }
        register("daggerHilt") {
            id = "serg.chuprin.dagger.hilt"
            implementationClass = "DaggerHiltConventionPlugin"
        }
        register("firebase") {
            id = "serg.chuprin.firebase"
            implementationClass = "FirebaseConventionPlugin"
        }
        register("dokka") {
            id = "serg.chuprin.dokka"
            implementationClass = "DokkaConventionPlugin"
        }
        // --- Feature Plugin (Das "Arbeitspferd") ---
        register("feature") {
            id = "serg.chuprin.feature"
            implementationClass = "FeatureConventionPlugin"
        }
        // --- Fähigkeits-Plugins (Capabilities) ---
        register("compose") {
            id = "serg.chuprin.compose"
            implementationClass = "ComposeConventionPlugin"
        }
        register("lint") {
            id = "serg.chuprin.lint"
            implementationClass = "LintConventionPlugin"
        }
        register("room") {
            id = "serg.chuprin.room"
            implementationClass = "RoomConventionPlugin"
        }
        // --- Tooling ---
        register("codeQuality") {
            id = "serg.chuprin.codequality"
            implementationClass = "CodeQualityConventionPlugin"
        }
        register("spotless") {
            id = "serg.chuprin.spotless"
            implementationClass = "SpotlessConventionPlugin"
        }
        register("detekt") {
            id = "serg.chuprin.detekt"
            implementationClass = "DetektConventionPlugin"
        }
    }
}

plugins {
    `kotlin-dsl`
}

group = "serg.chuprin.finances.buildlogic"

// Abhängigkeiten für die Build-Logik selbst
dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.detekt.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "finances.android.application"
            implementationClass = "ApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "finances.android.library"
            implementationClass = "LibraryConventionPlugin"
        }
        register("androidFeature") {
            id = "finances.android.feature"
            implementationClass = "FeatureConventionPlugin"
        }
        register("androidCompose") {
            id = "finances.android.compose"
            implementationClass = "ComposeConventionPlugin"
        }
        register("androidHilt") {
            id = "finances.android.hilt"
            implementationClass = "DaggerHiltConventionPlugin"
        }
        register("androidRoom") {
            id = "finances.android.room"
            implementationClass = "RoomConventionPlugin"
        }
        register("androidFirebase") {
            id = "finances.android.firebase"
            implementationClass = "FirebaseConventionPlugin"
        }
        register("codeQualityDetekt") {
            id = "finances.code.quality.detekt"
            implementationClass = "DetektConventionPlugin"
        }
        // ... weitere Plugins (Lint, Spotless etc.) falls nötig
    }
}