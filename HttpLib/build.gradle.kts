plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
//    id("maven-publish")
//    `java-library`
//    alias(libs.plugins.mavenPublish)
    `maven-publish`
//    `java-library` // 应用java-library插件，它会自动配置sourceSets

}

// 生成源码JAR的task
//tasks.register("sources", Jar::class) {
//    archiveClassifier.set("sources")
////    from(sourceSets.main.get().allSource)
//    from(sourceSets.get("main").allSource)
//}
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

//            afterEvaluate {
//                from(components["release"])
//            }
            // 添加源码JAR到出版物
//            artifact(tasks["sources"])
        }

        create<MavenPublication>("mavenJava") {
            groupId = "cn.wq"
            artifactId = "httpLibrary"
            version = "1.0"
//            from(components["java"])
//            artifact(tasks["sources"])
        }
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
////    publishing {
////        publications {
////            create<MavenPublication>("httpLibraryPublication") {
////                from(components["release"])
////                artifact(sourcesJar.get())
////            }
////        }
////    }
//}
android {
    namespace = "hz.wq.httplib"
    compileSdk = 34

    publishing {
//        multipleVariants {
//            includeBuildTypeValues("debug", "release")
//            withSourcesJar()
//            allVariants()
//            withJavadocJar()
//
//        }
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
//        multipleVariants("http") {
//            includeBuildTypeValues("debug", "release")
////            includeFlavorDimensionAndValues(
////                dimension = "color",
////                values = arrayOf("blue", "pink")
////            )
////            includeFlavorDimensionAndValues(
////                dimension = "shape",
////                values = arrayOf("square")
////            )
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