// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false

//    `maven-publish`
//    alias(libs.plugins.mavenPublish) apply false

}

group = "com.my-company"
version = "1.0.2"

