

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.crashlytics) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.devtools) apply false
    alias(libs.plugins.serialization) apply false
}

buildscript {
    dependencies {
        classpath(libs.secrets.gradle.plugin)
    }
}