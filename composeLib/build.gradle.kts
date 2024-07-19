plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    `maven-publish`
}
version = "0.1.4"
android {
    namespace = "hz.wq.composelib"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8

    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
//    api(libs.androidx.core.ktx)
//    api(libs.androidx.lifecycle.runtime.ktx)
//    api(libs.androidx.activity.compose)
//    api(platform(libs.androidx.compose.bom))
//    api(libs.androidx.ui)
//    api(libs.androidx.ui.graphics)
//    api(libs.androidx.ui.tooling.preview)
//    api(libs.androidx.material3)
    api(project(":common"))

//    testImplementation(libs.junit)
//    androidTestImplementation(libs.androidx.junit)
//    androidTestImplementation(libs.androidx.espresso.core)
//    androidTestImplementation(platform(libs.androidx.compose.bom))
//    androidTestImplementation(libs.androidx.ui.test.junit4)
//    debugImplementation(libs.androidx.ui.tooling)
//    debugImplementation(libs.androidx.ui.test.manifest)


    api("androidx.navigation:navigation-compose:2.7.5")
    // 如果你使用的是Hilt依赖注入框架
    api("androidx.hilt:hilt-navigation-compose:1.0.0")
    api("androidx.compose.runtime:runtime:1.5.1")
}


extra.set("libVersion", version)
extra.set("libArtifactId", "composeLib")
apply("../publishing.gradle.kts")