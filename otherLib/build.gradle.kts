plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    `maven-publish`
}
publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "cn.wq"
            artifactId = "otherLibrary"
            version = "1.0"
//
//            afterEvaluate {
//                from(components["release"])
//            }
        }
//        create<MavenPublication>("mavenJava") {
//            groupId = "cn.wq"
//            artifactId = "otherLibrary"
//            version = "1.0"
////            from(components["java"])
//        }
    }
//    repositories {
//        maven {
//            setUrl("$buildDir/repo")
//        }
//    }

}
android {
    namespace = "hz.wq.otherlib"
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}
// 定义 sourcesJar 任务
//val sourcesJar by tasks.registering(Jar::class) {
//    archiveClassifier.set("sources")
//    from(android.sourceSets["main"].java.srcDirs)
//}
//
//// 将 sourcesJar 作为一个工件
//afterEvaluate {
//    artifacts {
//        add("archives", sourcesJar.get())
//    }
//
//    publishing {
//        publications {
//            create<MavenPublication>("otherLibraryPublication") {
//                from(components["release"])
//                artifact(sourcesJar.get())
//            }
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