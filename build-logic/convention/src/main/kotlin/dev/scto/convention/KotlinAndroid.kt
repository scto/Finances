/*
 * Copyright 2024 Thomas Schmid
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package serg.chuprin.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

import serg.chuprin.convention.libs
import serg.chuprin.convention.javaVersion
import serg.chuprin.convention.version
import serg.chuprin.convention.versionInt
import serg.chuprin.convention.FinancesConfig

/**
 * Konfiguriert die grundlegenden Kotlin-Optionen für Android-Module.
 */
internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        compileSdk = FinancesConfig.compileSdk

        defaultConfig {
            minSdk = FinancesConfig.minSdk
        }

        compileOptions {
            sourceCompatibility = JavaVersion.toVersion(FinancesConfig.javaVersion)
            targetCompatibility = JavaVersion.toVersion(FinancesConfig.javaVersion)
        }
    }

    configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.toVersion(FinancesConfig.javaVersion)
        targetCompatibility = JavaVersion.toVersion(FinancesConfig.javaVersion)
    }

    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = FinancesConfig.javaVersion.toString()
            // Hier können weitere Compiler-Flags hinzugefügt werden
            freeCompilerArgs = freeCompilerArgs + listOf(
                "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api"
            )
        }
    }
}
