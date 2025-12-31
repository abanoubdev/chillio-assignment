import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.ksp)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.kotlin.serialization)
}

val javaVersion: JavaVersion by rootProject.extra
val projectCompileSdk: Int by rootProject.extra
val projectMinSdk: Int by rootProject.extra

android {
    namespace = "net.chillio.service"
    compileSdk = projectCompileSdk

    defaultConfig {
        minSdk = projectMinSdk
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
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }
    kotlin {
        compilerOptions {
            jvmTarget = JvmTarget.fromTarget(javaVersion.toString())
        }
    }
}

dependencies {
    // Hilt
    implementation(libs.com.google.dagger.hilt.android)
    ksp(libs.com.google.dagger.hilt.android.compiler)

    // Network
    implementation(libs.com.squareup.retrofit2.retrofit)
    implementation(libs.com.squareup.retrofit2.converter.scalars)

    // Serialization
    implementation(libs.org.jetbrains.kotlinx.serialization.json)
    implementation(libs.org.jetbrains.kotlinx.serialization.core)
    implementation(libs.com.jakewharton.retrofit.retrofit2.kotlinx.serialization.converter)

    // Debug
    debugImplementation(libs.com.github.chuckerteam.chucker)
    releaseImplementation(libs.com.github.chuckerteam.chucker.no.op)

    // Unit tests
    testImplementation(libs.junit)
    testImplementation(libs.com.google.dagger.hilt.android.testing)
    kspTest(libs.com.google.dagger.hilt.android.compiler)

    // Unit tests
    testImplementation(libs.junit)
    testImplementation(libs.com.google.dagger.hilt.android.testing)
    kspTest(libs.com.google.dagger.hilt.android.compiler)
    testImplementation(libs.org.jetbrains.kotlinx.coroutines.test)
    testImplementation(libs.org.jetbrains.kotlin.test)
    testImplementation(libs.org.mockito.kotlin)
    testImplementation(libs.org.mockito.inline)
    testImplementation(libs.org.mockito.core)
    testImplementation(libs.org.robolectric)

    // Instrumented tests
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
}