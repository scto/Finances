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

plugins {
    `kotlin-dsl`
}

group = "serg.chuprin.finances.buildlogic"

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.compiler.gradlePlugin)
    compileOnly(libs.detekt.gradlePlugin)
  // NEU: Spotless Plugin
    compileOnly(libs.spotless.gradlePlugin
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "finances.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "finances.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidFeature") {
            id = "finances.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
        register("androidCompose") {
            id = "finances.android.compose"
            implementationClass = "AndroidComposeConventionPlugin"
        }
        // Neu registriert: Detekt Plugin
        register("detekt") {
            id = "finances.detekt"
            implementationClass = "DetektConventionPlugin"
        }
        // NEU: Spotless Plugin Registrierung
        register("spotless") {
            id = "finances.spotless"
            implementationClass = "SpotlessConventionPlugin"
        }
    }
}