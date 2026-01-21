import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import com.android.build.gradle.LibraryExtension

class FeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("finances.android.library")
                apply("finances.android.hilt")
                // Empfehlung: Wenn alle Features UI haben, hier Compose aktivieren.
                // Falls es reine Logic-Features gibt, dies weglassen.
                // apply("finances.android.compose") 
            }
            extensions.configure<LibraryExtension> {
                defaultConfig {
                    // Test Runner für Features standardisieren
                    testInstrumentationRunner = "serg.chuprin.finances.core.test.FinancesTestRunner"
                }
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            dependencies {
                // ARCHITEKTUR-FIX:
                // Abhängigkeit zu ":core:impl" entfernt. Features sollten nur gegen die API programmieren.
                // Die Implementierung wird im :app Modul zur Laufzeit via Hilt bereitgestellt.
                add("implementation", project(":core:api"))
                // add("implementation", project(":core:impl")) // <- Entfernt

                add("implementation", project(":core:mvi"))

                add("implementation", libs.findLibrary("kotlin.coroutines").get())
                add("implementation", libs.findLibrary("kotlin.coroutines.android").get())
                add("implementation", libs.findLibrary("timber").get())

                // Testing dependencies
                add("testImplementation", project(":core:test"))
            }
        }
    }
}