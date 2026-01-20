plugins {
    id("finances.android.library")
    // Google Services Plugin für Firebase
    id("com.google.gms.google-services")
}

android {
    namespace = "serg.chuprin.finances.core.firebase"
}

dependencies {
    implementation(project(":core:api"))

    // --- Firebase & Google Services ---
    // Importiert die Firebase BOM über platform()
    implementation(platform(libs.firebase.bom))
    
    // Verwendet das Bundle für Analytics, Auth, Firestore, Crashlytics
    api(libs.bundles.firebase)
    
    // Google Play Services Auth (separat, da nicht im Firebase Bundle)
    api(libs.play.services.auth)
    
    // Coroutines Play Services Integration (für await() Extension Functions)
    api(libs.kotlin.coroutines.play.services)
}