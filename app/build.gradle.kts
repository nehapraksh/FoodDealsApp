plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.android.dagger.hilt)
    alias(libs.plugins.compose.compiler)
    kotlin("kapt")
}

android {
    namespace = "com.example.fooddeals"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.fooddeals"
        minSdk = 28
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.foundation)
    implementation(libs.androidx.ui.tooling)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    //Hilt Dependencies - Dependency Injection
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    // OPTIONAL: For instrumentation tests
    androidTestImplementation (libs.hilt.android.test)
    kaptAndroidTest (libs.hilt.compiler)
    // OPTIONAL: For local unit tests
    testImplementation (libs.hilt.android.test)
    kaptTest (libs.hilt.compiler)

    // view model
    implementation (libs.viewmodel.ktx)
    kapt(libs.viewmodel.compiler)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // network components
    implementation (libs.retrofit)
    implementation (libs.gson)
    implementation (libs.converter.gson)
    implementation (libs.logging.interceptor)

    //Coroutines
    implementation (libs.coroutines.core)
    testImplementation (libs.coroutines.test)

    // navigation graph
    implementation(libs.navigation)
    implementation(libs.navigation.ui)
    // navigation animation
    implementation(libs.navigation.animation)
    //compose
    implementation(libs.compose.runtime)
    implementation(libs.coil.compose)
    implementation(libs.compose.navigation)
    implementation(libs.compose.material3)
    // Android Studio Preview support
    implementation(libs.androidx.ui.tooling.preview)

    // Mockito
    testImplementation(libs.mockito.core)
    testImplementation(libs.hamcrest)
    testImplementation(libs.mockk)
    testImplementation(libs.okhttp3.mockwebserver)
    testImplementation(libs.arch.core)


}