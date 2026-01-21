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

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.dokka.gradle.DokkaExtension
import org.jetbrains.dokka.gradle.engine.plugins.DokkaHtmlPluginParameters

class DokkaConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("org.jetbrains.dokka")

            extensions.configure<DokkaExtension> {
                // Setzt den Modulnamen auf den Gradle-Pfad (z.B. :app oder :core:ui)
                moduleName.set(path)

                // Konfiguration des SourceSets "main"
                dokkaSourceSets.named("main") {
                    // Bindet die README.md in die Dokumentation ein
                    includes.from("README.md")
                    
                    // Unterdrückt generierte Dateien (z.B. durch KSP/Kapt) in der Doku
                    suppressGeneratedFiles.set(true)
                }

                // Konfiguration des HTML-Outputs (Footer Text)
                pluginsConfiguration.withType<DokkaHtmlPluginParameters> {
                    footerMessage.set("Made with ❤\uFE0F by Thomas Schmidl")
                }
            }

            // Zugriff auf den Version Catalog 'libs'
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            dependencies {
                // Fügt Dokka-Plugins hinzu (Android & Mermaid Support)
                add("dokkaPlugin", libs.findLibrary("dokka.android.plugin").get())
                add("dokkaPlugin", libs.findLibrary("dokka.mermaid.plugin").get())
            }
        }
    }
}