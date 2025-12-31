import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.ksp)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.dagger.hilt.android)
}

val javaVersion: JavaVersion by rootProject.extra
val projectCompileSdk: Int by rootProject.extra
val projectMinSdk: Int by rootProject.extra
val projectTargetSdk: Int by rootProject.extra

android {
    namespace = "net.chillio"
    compileSdk = projectCompileSdk

    defaultConfig {
        applicationId = "net.chillio"
        minSdk = projectMinSdk
        targetSdk = projectTargetSdk
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
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }

    kotlin {
        compilerOptions {
            jvmTarget = JvmTarget.fromTarget(javaVersion.toString())
        }
    }

    buildFeatures {
        compose = true
    }
}

dependencies {

    //Modules
    implementation(project(":core:ui"))
    implementation(project(":core:designsystem"))
    implementation(project(":feature:employeelist"))

    // Core
    implementation(libs.androidx.core.ktx)

    // UI
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Navigation
    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.navigation3.ui)
    implementation(libs.androidx.lifecycle.viewmodel.navigation3)

    // Hilt
    implementation(libs.com.google.dagger.hilt.android)
    ksp(libs.com.google.dagger.hilt.android.compiler)

    // Serialization
    implementation(libs.org.jetbrains.kotlinx.serialization.json)
    implementation(libs.org.jetbrains.kotlinx.serialization.core)
    implementation(libs.org.jetbrains.kotlinx.serialization.protobuf)

}