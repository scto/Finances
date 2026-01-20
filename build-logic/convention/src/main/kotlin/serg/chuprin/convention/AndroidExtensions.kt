package serg.chuprin.convention

import com.android.build.api.dsl.CommonExtension

/**
 * Aktiviert die Generierung der BuildConfig-Klasse.
 * Benötigt 6 generische Wildcards für AGP 8+.
 */
fun CommonExtension<*, *, *, *, *, *>.enableBuildConfig() {
    buildFeatures.buildConfig = true
}

/**
 * Aktiviert ViewBinding.
 */
fun CommonExtension<*, *, *, *, *, *>.enableViewBinding() {
    buildFeatures.viewBinding = true
}

/**
 * Aktiviert Jetpack Compose.
 */
fun CommonExtension<*, *, *, *, *, *>.enableCompose() {
    buildFeatures.compose = true
}