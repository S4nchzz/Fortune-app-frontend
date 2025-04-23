import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(localPropertiesFile.inputStream())
}

android {
    namespace = "com.fortune.app"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.fortune.app"
        minSdk = 33
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            val apiUrl = localProperties.getProperty("API_URL")
            buildConfigField("String", "API_URL", "\"${apiUrl}\"")
        }

        debug {
            val apiUrl = localProperties.getProperty("API_URL")
            buildConfigField("String", "API_URL", "\"${apiUrl}\"")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx.v1120)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.appcompat.v161)
    implementation(libs.kotlin.stdlib) // Kotlin Standard Library
    implementation(libs.androidx.core.ktx.v132)           // Core KTX
    implementation(libs.androidx.appcompat.v120)      // App Compat
    implementation(libs.material) // Material Design
    implementation(libs.androidx.constraintlayout.v204) // Constraint Layout

    implementation(libs.androidx.fragment.ktx)    // Fragment KTX
    implementation(libs.androidx.activity.ktx)    // Activity KTX
    implementation(libs.androidx.lifecycle.viewmodel.ktx.v231) // ViewModel KTX
    implementation(libs.androidx.lifecycle.livedata.ktx.v231)  // LiveData KTX

    implementation(libs.retrofit)       // Retrofit
    implementation(libs.converter.gson)  // Retrofit Gson Converter

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.activity) // Room
    kapt(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    implementation(libs.androidx.recyclerview)

    implementation(libs.kotlinx.coroutines.android.v136) // Kotlin Coroutines

    androidTestImplementation(libs.androidx.junit)     // Android Test Ext JUnit (Testing)
    androidTestImplementation(libs.androidx.espresso.core.v330) // Espresso Core (Testing)

    implementation(libs.hilt.android.v2511)
    kapt(libs.hilt.android.compiler.v2511)

    implementation(libs.lottie) // lottie

    testImplementation(libs.junit.jupiter)

    implementation(libs.mpandroidchart)
}

kapt {
    correctErrorTypes = true
}