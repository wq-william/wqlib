plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    `maven-publish`
}



android {
    namespace = "hz.wq.httplib"
    compileSdk = 34

    publishing {
        multipleVariants {
            includeBuildTypeValues("debug", "release")
            withSourcesJar()
            allVariants()
            withJavadocJar()

        }
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
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
val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(android.sourceSets["main"].java.srcDirs)
}
publishing {

    publications {
        create<MavenPublication>("httpLibraryPublication") {
//            from(components["release"])

            // 仅添加一个 sourcesJar
            artifact(sourcesJar.get())
        }
        register<MavenPublication>("release") {
            groupId = "cn.wq"
            artifactId = "httpLibrary"
            version = "1.0"

//            afterEvaluate {
//                from(components["release"])
//            }
            // 添加源码JAR到出版物
           artifact(sourcesJar.get())
        }
//
//        create<MavenPublication>("mavenJava") {
//            groupId = "cn.wq"
//            artifactId = "httpLibrary"
//            version = "1.0"
////            from(components["java"])
////            artifact(tasks["sources"])
//        }
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