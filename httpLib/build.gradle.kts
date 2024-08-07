plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    `maven-publish`
}

version = "0.2.1"

android {
    namespace = "hz.wq.httplib"
    compileSdk = 34
    testFixtures {
        enable = true
    }
    publishing {
        multipleVariants {
            withSourcesJar()
            allVariants()
            withJavadocJar()
        }
//        singleVariant("release") {
//            withSourcesJar()
//            withJavadocJar()
//        }
    }
    defaultConfig {
        aarMetadata {
            minSdk = 24
        }
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    api(project(":common"))
//    implementation(libs.androidx.core.ktx)
//    implementation(libs.androidx.appcompat)
//    implementation(libs.material)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // retrofit2
//    implementation(libs.retrofit)
    api("com.squareup.retrofit2:retrofit:2.9.0")
    api("com.squareup.retrofit2:converter-moshi:2.9.0")
    api("com.squareup.retrofit2:converter-gson:2.9.0")
    api("com.squareup.retrofit2:converter-scalars:2.9.0")
    api("com.squareup.retrofit2:retrofit-mock:2.9.0")

    api("com.squareup.okhttp3:logging-interceptor:3.12.2")
    api("com.blankj:utilcodex:1.31.1")


    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
}

extra.set("libVersion", version)
extra.set("libArtifactId", "httpLib")
apply("../publishing.gradle.kts")