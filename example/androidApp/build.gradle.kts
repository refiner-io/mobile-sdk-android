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
    signingConfigs {
        getByName("debug") {
            storeFile = file("debug.keystore")
            storePassword = "android"
            keyAlias = "androiddebugkey"
            keyPassword = "android"
        }
    }

    buildTypes {
        debug {
            signingConfig = signingConfigs.getByName("debug")
        }
        release {
            // Caution! In production, you need to generate your own keystore file.
            signingConfig = signingConfigs.getByName("debug")
            isMinifyEnabled = true
            proguardFiles("proguard-android-optimize.txt", "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(AndroidX.appCompat)
    implementation(AndroidX.constraintLayout)
    implementation(AndroidX.core.ktx)
    implementation(AndroidX.activity.ktx)
    implementation("io.refiner:refiner:1.5.8")
}
