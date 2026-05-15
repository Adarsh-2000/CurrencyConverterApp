plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    // Kotlin Symbol Processing (KSP) Plugin
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.currencyconverterapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.currencyconverterapp"
        minSdk = 30
        targetSdk = 35
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
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Testing Dependencies
    testImplementation(libs.junit) // JUnit for Unit Testing
    androidTestImplementation(libs.androidx.junit) // AndroidX JUnit
    androidTestImplementation(libs.androidx.espresso.core) // Espresso for UI Testing
    androidTestImplementation(platform(libs.androidx.compose.bom)) // Compose UI Testing
    androidTestImplementation(libs.androidx.ui.test.junit4) // Jetpack Compose UI Testing
    debugImplementation(libs.androidx.ui.tooling) // Compose Debug Tooling
    debugImplementation(libs.androidx.ui.test.manifest) // Compose UI Test Manifest
    implementation(libs.androidx.navigation.testing) // Navigation Testing Tools

    // Hilt - Dependency Injection
    // Core Hilt Library
    implementation(libs.hilt.android)
    // Hilt Navigation for Jetpack Compose
    implementation(libs.androidx.hilt.navigation.compose)
    // Hilt Compiler for annotation processing
    ksp(libs.hilt.compiler)

    // Retrofit - API Networking
    implementation(libs.retrofit)
    // JSON Converter (Gson)
    implementation(libs.converter.gson)
    // Logging Interceptor (Debugging API Calls)
    implementation(libs.logging.interceptor)


    // Room Database - Local Storage
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.runtime)

    // Paging - Infinite Scrolling Support
    implementation(libs.androidx.paging.runtime.ktx)
    implementation(libs.androidx.paging.compose)

    // Room-Paging Integration
    implementation(libs.androidx.room.paging)

    // Unit Testing
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk)
    testImplementation(libs.turbine)

    // For Hilt ViewModel tests
    androidTestImplementation(libs.hilt.android.testing)
    // For Hilt test compiler
    kspAndroidTest(libs.hilt.android.compiler)

    // Core testing
    androidTestImplementation(libs.androidx.core)
    androidTestImplementation(libs.androidx.junit.v115)
    androidTestImplementation(libs.androidx.espresso.core.v351)

    // orkManager with Hilt
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.hilt.work)
    ksp(libs.androidx.hilt.compiler)
    testImplementation(kotlin("test"))

    testImplementation(libs.mockito.kotlin)
}