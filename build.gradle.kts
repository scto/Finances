import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.TestedExtension
import com.android.build.gradle.internal.dsl.BuildType
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension

import com.vanniktech.dependency.graph.generator.DependencyGraphGeneratorExtension

import guru.nidi.graphviz.attribute.Color
import guru.nidi.graphviz.attribute.Style
import guru.nidi.graphviz.model.MutableNode

import org.jetbrains.kotlin.gradle.internal.AndroidExtensionsExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

import serg.chuprin.finances.config.AppConfig
import serg.chuprin.finances.config.setIsDebugMenuEnabled
import serg.chuprin.finances.config.enableBuildConfig // WICHTIG: Import der Extension Function
import serg.chuprin.finances.config.enableViewBinding // WICHTIG: Import der Extension Function


// Plugins Block
plugins {
  id("com.github.ben-manes.versions") version "0.53.0"
  id("com.vanniktech.dependency.graph.generator") version "0.5.0"
  //id("com.vanniktech.dependency.graph.generator") version "0.8.0"
  id("io.gitlab.arturbosch.detekt") version "1.23.5" // Nutze eine aktuelle Version!
  //alias(libs.plugins.plugin.detekt.gradle)
  //alias(libs.plugins.plugin.android.gradle) apply false
  //alias(libs.plugins.plugin.kotlin.gradle) apply false
}

buildscript {
  repositories {
    google()
    mavenCentral()
    maven {
      url = uri("https://jitpack.io")
    }
    maven {
      url = uri("https://jcenter.bintray.com/")
    } // Wichtig für serg.chuprin
    gradlePluginPortal()
  }
  dependencies {
    classpath(BuildScript.Plugins.GMS)
    classpath(BuildScript.Plugins.KOTLIN)
    classpath(BuildScript.Plugins.JUNIT5)
    classpath(BuildScript.Plugins.ANDROID)
    classpath(BuildScript.Plugins.NAVIGATION)
    classpath(BuildScript.Plugins.GRAPH_VISUALIZER)
    classpath(BuildScript.Plugins.PROGUARD_GENERATOR)
  }
}

/*
allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://jcenter.bintray.com/") } // Wichtig für serg.chuprin
    }
}
*/

subprojects {
  addKotlinCompilerFlags()
  forceDependencyVersions()

  // Automatische Konfiguration für Android Libraries (wie :core:api)
  pluginManager.withPlugin("com.android.library") {
    extensions.configure<LibraryExtension> {
      // FEHLERBEHEBUNG:
      // Aktiviert BuildConfig global für alle Libraries.
      // Dies behebt den Fehler ":core:api - Build Type 'debug' contains custom BuildConfig fields..."
      enableBuildConfig()
    }
  }

  // Automatische Konfiguration für Android Apps (falls benötigt)
  pluginManager.withPlugin("com.android.application") {
    extensions.configure<BaseAppModuleExtension> {
      enableBuildConfig()
      // Optional: ViewBinding standardmäßig aktivieren, falls gewünscht
      enableViewBinding()
    }
  }

  afterEvaluate {
    // Sicherer Zugriff auf die Android Extension (App oder Library)
    extensions.findByType<TestedExtension>()?.apply {
      enableExperimentalKotlinExtensions(project)

      defaultConfig {
        // Annahme: VersionCode/Name sind Int/String in AppConfig
        versionCode = AppConfig.VERSION_CODE
        versionName = AppConfig.VERSION_NAME
        minSdk = AppConfig.MIN_SDK
        targetSdk = AppConfig.TARGET_SDK
      }

      configureBuildTypes()

      compileSdkVersion(AppConfig.TARGET_SDK)
      buildToolsVersion("31.0.0") // 35.0.0 / 36.0.0

      sourceSets.forEach {
        sourceSet ->
        sourceSet.java.srcDir("src/${sourceSet.name}/kotlin")
      }

      compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
      }

      packagingOptions {
        resources.excludes.add("META-INF/DEPENDENCIES")
        resources.excludes.add("META-INF/AL2.0")
        resources.excludes.add("META-INF/LGPL2.1")
      }

      configureSpek(project, this)
      enableDesugaring(project, this)

      // "group" ist in Gradle Projekten ein Object, toString() ist nötig
      if (project.group.toString().contains("feature", ignoreCase = true)) {
        dependencies.add("implementation", Libraries.EDGE_TO_EDGE)
      }
    }
  }
}

// Konfiguration des Dependency Graphs
configure<DependencyGraphGeneratorExtension> {
  generators = listOf(
    DependencyGraphGeneratorExtension.Generator(
      name = "Modules",
      children = {
        false
      },
      include = {
        dependency ->
        dependency.moduleGroup.startsWith("finances", ignoreCase = true)
      },
      dependencyNode = {
        node: MutableNode, _ ->
        node.add(Style.FILLED, Color.rgb("#FFCB2B"))
      }
    )
  )
}

fun enableDesugaring(project: Project, testedExtension: TestedExtension) {
  testedExtension.compileOptions.isCoreLibraryDesugaringEnabled = true
  project.dependencies.add(
    "coreLibraryDesugaring",
    "com.android.tools:desugar_jdk_libs:2.1.5"
  )
}

/**
 * Konfiguriert Test-Optionen für Spek auf Android und fügt Abhängigkeiten hinzu.
 */
fun configureSpek(project: Project, testedExtension: TestedExtension) {
  project.plugins.apply("de.mannodermaus.android-junit5")

  with(testedExtension) {
    testOptions {
      unitTests.all {
        test ->
        test.useJUnitPlatform {
          includeEngines("spek2")
        }
        test.systemProperty("kotlinx.coroutines.debug", "on")
        test.testLogging.events("passed", "skipped", "failed")
      }
    }
  }

  project.dependencies {
    add("testImplementation", Libraries.Tests.JUPITER_API)
    add("testRuntimeOnly", Libraries.Tests.JUPITER_ENGINE)

    add("testImplementation", Libraries.Tests.SPEK_JVM)
    add("testImplementation", Libraries.Tests.SPEK_RUNNER)
    add("testImplementation", Libraries.Tests.KOTLIN_REFLECT)

    add("testImplementation", Libraries.Tests.MOCKK)
    add("testImplementation", Libraries.Tests.STRIKT)
    add("testImplementation", Libraries.Tests.FILE_PEEK)
    add("testImplementation", Libraries.Tests.ASSERTIONS)

    add("testImplementation", Libraries.Tests.COROUTINES)
    add("testImplementation", Libraries.Tests.COROUTINES_DEBUG)
  }
}

// Setzt Build Types für Android Module
fun TestedExtension.configureBuildTypes() {

  fun BuildType.configProguard(isLibrary: Boolean) {
    if (isLibrary) {
      consumerProguardFile("proguard-rules.pro")
    } else {
      proguardFiles(
        "proguard-rules.pro",
        getDefaultProguardFile("proguard-android-optimize.txt")
      )
    }
  }

  val isLibrary = this is LibraryExtension

  buildTypes {
    maybeCreate(AppConfig.BuildTypes.RELEASE.name).apply {
      isMinifyEnabled = true
      isDebuggable = false
      configProguard(isLibrary)
      setIsDebugMenuEnabled(false)
    }
    maybeCreate(AppConfig.BuildTypes.DEBUG.name).apply {
      isMinifyEnabled = true
      isDebuggable = false
      configProguard(isLibrary)
      setIsDebugMenuEnabled(true)
    }
    maybeCreate(AppConfig.BuildTypes.DEV.name).apply {
      isDebuggable = true
      setIsDebugMenuEnabled(true)
    }
  }
}

fun Project.addKotlinCompilerFlags() {
  tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
      jvmTarget = "17"
      freeCompilerArgs = freeCompilerArgs + listOf(
        "-XXLanguage:+InlineClasses",
        "-Xallow-result-return-type",
        "-opt-in=kotlin.RequiresOptIn", // Aktualisierte Syntax für opt-in
        "-opt-in=kotlin.ExperimentalStdlibApi"
      )
    }
  }
}

fun enableExperimentalKotlinExtensions(project: Project) {
  // Hinweis: AndroidExtensions sind deprecated und wurden in neueren Kotlin Versionen entfernt.
  project.extensions.findByType<AndroidExtensionsExtension>()?.isExperimental = true
}

fun Project.forceDependencyVersions() {
  configurations.all {
    resolutionStrategy {
      force(Libraries.KOTLIN)
    }
  }
}

// Detekt Konfiguration (Statische Codeanalyse)
/*
detekt {
    toolVersion = libs.versions.detekt.get()
    config = files("config/detekt/detekt.yml")
    buildUponDefaultConfig = true
}

detekt {
  toolVersion = "1.23.5"
  config = files("config/detekt/detekt.yml") // Optional: Pfad zu deiner Config
  buildUponDefaultConfig = true
}

tasks.register<Delete>("clean") {
  delete(rootProject.layout.buildDirectory)
}
*/