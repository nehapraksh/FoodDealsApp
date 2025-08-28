import org.jetbrains.kotlin.commonizer.OptimisticNumberCommonizationEnabledKey.alias
import java.net.URI

// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
        maven { url = uri( "https://www.jitpack.io") }
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.android.dagger.hilt) apply false
    alias(libs.plugins.compose.compiler) apply false
}