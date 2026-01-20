import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

import serg.chuprin.convention.FinancesConfig

class DetektConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("io.gitlab.arturbosch.detekt")

            val extension = extensions.getByType<DetektExtension>()
            extension.apply {
                toolVersion = libs.findVersion("detekt").get().toString()
                
                source.setFrom(
                    files(
                        "src/main/java",
                        "src/main/kotlin",
                        "src/test/java",
                        "src/test/kotlin",
                        "src/androidTest/java",
                        "src/androidTest/kotlin"
                    )
                )
                
                config.setFrom(files("${project.rootDir}/config/detekt/detekt.yml"))
                
                buildUponDefaultConfig = true
                parallel = true
                autoCorrect = true
            }

            dependencies {
                // Standard Formatierung (KtLint Wrapper)
                add("detektPlugins", libs.findLibrary("detekt-formatting").get())
                
                // Compose spezifische Regeln (Twitter/mrmans0n rules)
                add("detektPlugins", libs.findLibrary("detekt-compose").get())
            }
        }
    }
}