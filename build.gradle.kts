// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    id("com.google.dagger.hilt.android") version "2.48" apply false
    kotlin("jvm") version "1.9.0" apply false
//    `maven-publish`
//    alias(libs.plugins.mavenPublish) apply false

}

//group = "com.my-company"
//version = "1.0.2"

