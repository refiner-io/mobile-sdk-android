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
    namespace = "io.refiner.android.app"

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlin {
        jvmToolchain(11)
    }
}

dependencies {
    implementation(AndroidX.appCompat)
    implementation(AndroidX.constraintLayout)
    implementation(AndroidX.core.ktx)
    implementation(AndroidX.activity.ktx)
    implementation("io.refiner:refiner:1.3.0")
}
