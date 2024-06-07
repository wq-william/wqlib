plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
//    id("maven-publish")
//    `java-library`
//    alias(libs.plugins.mavenPublish)
    `maven-publish`

}
//afterEvaluate {
//    android.libraryVariants.each{
//        publishing.publications.create(variant.name,MavenPublication){
//            from components.findByName(variant.name)
//            groupId = 'com.yangzaiarea'//自定义
//            artifactId = 'yzutils'//自定义
//            version = '1.0.0'//自定义
//        }
//    }
//}
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
            groupId = "cn.wq"
            artifactId = "httpLibrary"
            version = "1.0"

            afterEvaluate {
                from(components["release"])
            }
        }

//        create<MavenPublication>("mavenJava") {
//            groupId = "cn.wq"
//            artifactId = "httpLibrary"
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
//            create<MavenPublication>("httpLibraryPublication") {
//                from(components["release"])
//                artifact(sourcesJar.get())
//            }
//        }
//    }
//}
android {
    namespace = "hz.wq.httplib"
    compileSdk = 34

    publishing {
        multipleVariants {
            withSourcesJar()
            allVariants()
            withJavadocJar()

        }
//        singleVariant("release") {
//            withSourcesJar()
//
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}