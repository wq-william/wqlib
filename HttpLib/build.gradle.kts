plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
//    id("maven-publish")
    `maven-publish`
}
//publishing {
//    publications {
//        create<MavenPublication>("mavenJava") {
////            from(components["release"])
//            from(components["default"])
//            groupId = "hz.wqq.http.api"
//            artifactId = "lib"
//            version = "0.0.2"
//        }
//    }
//}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "com.my-company"
            artifactId = "my-library"
            version = "1.0"

            afterEvaluate {
                from(components["release"])
            }
        }
    }
}
android {
    namespace = "hz.wq.httplib"
    compileSdk = 34

    publishing {
//        multipleVariants {
//            allVariants()
//            withJavadocJar()
//        }
        singleVariant("release") {
            withSourcesJar()

        }


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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

//publishing {
//    publications {
//        create<MavenPublication>("release") {
////            from(components["release"])
////            components["default"]
//            groupId = "com.github.yourusername"
//            artifactId = "module1"
//            version = "1.0.0"
//        }
//    }
//}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}