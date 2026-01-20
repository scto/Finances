import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val extension = extensions.findByType(ApplicationExtension::class.java) 
                ?: extensions.findByType(LibraryExtension::class.java)
            
            (extension as? CommonExtension<*, *, *, *, *, *>)?.apply {
                buildFeatures {
                    compose = true
                }

                composeOptions {
                    kotlinCompilerExtensionVersion = libs.findVersion("androidxComposeCompiler").get().toString()
                }
                
                // Compose Dependencies hinzufügen
                dependencies.add("implementation", platform(libs.findLibrary("androidx.compose.bom").get()))
                dependencies.add("implementation", libs.findLibrary("androidx.ui").get())
                dependencies.add("implementation", libs.findLibrary("androidx.ui.graphics").get())
                dependencies.add("implementation", libs.findLibrary("androidx.ui.tooling.preview").get())
                dependencies.add("debugImplementation", libs.findLibrary("androidx.ui.tooling").get())
            }
        }
    }
}
