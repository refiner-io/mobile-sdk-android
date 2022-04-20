plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdk = project.property("COMPILE_SDK").toString().toInt()
    defaultConfig {
        applicationId = "io.refiner.android.app"
        minSdk = project.property("MIN_SDK").toString().toInt()
        targetSdk = project.property("TARGET_SDK").toString().toInt()
        versionCode = 1
        versionName = "0.0.1"
    }
}

dependencies {
    implementation("io.refiner:refiner:+")
    implementation(AndroidX.appCompat)
    implementation(AndroidX.constraintLayout)
    implementation(AndroidX.core.ktx)
    implementation(AndroidX.activity.ktx)
}
