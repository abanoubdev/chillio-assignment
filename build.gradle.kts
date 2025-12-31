// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript{
    val javaVersion by extra(JavaVersion.VERSION_17)
    val projectCompileSdk by extra(36)
    val projectMinSdk by extra(29)
    val projectTargetSdk by extra(36)
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.ksp) apply false
    alias(libs.plugins.dagger.hilt.android) apply false
    alias(libs.plugins.kotlin.serialization) apply false
}