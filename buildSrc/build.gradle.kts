plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    // AGP 8.11.1 wurde mit Kotlin 2.0 kompiliert.
    // Wir müssen sicherstellen, dass buildSrc auch eine kompatible Version verwendet.
    implementation("com.android.tools.build:gradle:8.11.1")
    
    // Update auf Kotlin 2.0.21, um den Metadata-Fehler zu beheben
    implementation(kotlin("gradle-plugin", "2.0.21"))
    
    // Standard Kotlin Standard Library (oft implizit, aber sicherheitshalber hier)
    implementation(kotlin("stdlib", "2.0.21"))
}