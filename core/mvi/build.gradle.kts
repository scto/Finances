plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    namespace = "serg.chuprin.finances.core.test.presentation.mvi.utils"
}

dependencies {
    implementation(Libraries.KOTLIN)
    implementation(Libraries.Coroutines.CORE)
    api(Libraries.Tests.COROUTINES)
    implementation(Libraries.Tests.COROUTINES_DEBUG)

    // Timber.
    implementation(Libraries.TIMBER)

}