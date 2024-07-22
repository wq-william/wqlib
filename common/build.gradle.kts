import org.gradle.internal.component.model.Exclude
import org.jetbrains.kotlin.cli.jvm.main

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    `maven-publish`
}
version = "0.1"

android {
    namespace = "hz.wq.common"
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
    viewBinding {
        enable = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
//    sourceSets {
//        val main = getByName<SourceSet>("main")
//        main.resources.srcDirs(
//            "src/main/res",
//            "src/main/res-sw"
//        )
//    }
//    sourceSets {
//        val main by getting<AndroidSourceSet>()
//        main.res.srcDirs("src/main/res", "src/main/res-sw")
//    }
//    sourceSets {
//        main {
//            // res 资源目录配置
//            res.srcDirs(
//                'src/main/res',
//                'src/main/res-sw',
//            )
//        }
//    }
}

dependencies {
    api(libs.androidx.core.ktx)
    api(libs.androidx.lifecycle.runtime.ktx)
    api(libs.androidx.activity.compose)
    api(platform(libs.androidx.compose.bom))
    api(libs.androidx.ui)
    api(libs.androidx.ui.graphics)
    api(libs.androidx.ui.tooling.preview)
    api(libs.androidx.material3)
    api(libs.material)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    api("com.blankj:utilcodex:1.31.1")


    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
}

extra.set("libVersion", version)
extra.set("libArtifactId", "common")
apply("../publishing.gradle.kts")