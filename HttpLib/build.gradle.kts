plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    `maven-publish`
}
publishing {
    publications {
        create<MavenPublication>("release") {
            groupId = "io.github.wq-william"
            artifactId = "httpLibrary"
            version = "0.0.5"
            afterEvaluate {
                from(components["release"])
            }
            pom {
                name = "My Library"
                description = "A concise description of my library"
                url = "https://github.com/wq-william/wqlib"
                properties = mapOf(
                    "myProp" to "value",
                    "prop.with.dots" to "anotherValue"
                )
                licenses {
                    license {
                        name = "The Apache License, Version 2.0"
                        url = "http://www.apache.org/licenses/LICENSE-2.0.txt"
                    }
                }
                developers {
                    developer {
                        id = "github_13477226"
                        name = "415906626@qq.com"
                        email = "415906626@qq.com"
                    }
                }
                scm {
                    connection = "scm:git:git://github.com/wq-william/wqlib.git"
                    developerConnection = "scm:git:ssh://github.com/wq-william/wqlib.git"
                    url = "https://github.com/wq-william/wqlib"
                }
            }
        }

    }

}

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