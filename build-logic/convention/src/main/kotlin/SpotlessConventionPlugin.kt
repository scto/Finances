import com.diffplug.gradle.spotless.SpotlessExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class SpotlessConventionPlugin : Plugin<Project> {
  override fun apply(target: Project) {
    with(target) {
      pluginManager.apply("com.diffplug.spotless")

      extensions.configure<SpotlessExtension> {
        kotlin {
          target("**/*.kt")
          targetExclude("**/build/**/*.kt")

          // Verwendet die im Projekt definierte ktlint version oder eine Standardversion
          // Du kannst hier auch eine spezifische Version setzen: ktlint("1.0.1")
          ktlint()

          // Entfernt ungenutzte Imports
          trimTrailingWhitespace()
          indentWithSpaces()
          endWithNewline()

          // Lizenz-Header Konfiguration
          // Wir lesen die copyright.kt Datei aus dem Root-Config Ordner
          val licenseHeaderFile = rootProject.file("config/spotless/copyright.kt")
          if (licenseHeaderFile.exists()) {
            licenseHeaderFile(licenseHeaderFile)
          }
        }

        kotlinGradle {
          target("*.kts")
          targetExclude("**/build/**/*.kts")
          ktlint()
          trimTrailingWhitespace()
          indentWithSpaces()
          endWithNewline()

          val licenseHeaderFile = rootProject.file("config/spotless/copyright.kts")
          if (licenseHeaderFile.exists()) {
            licenseHeaderFile(licenseHeaderFile, "(^(?![\\/ ]\\*).*$)")
          }
        }

        format("xml") {
          target("**/*.xml")
          targetExclude("**/build/**/*.xml")
          trimTrailingWhitespace()
          indentWithSpaces()
          endWithNewline()
        }
      }
    }
  }
}